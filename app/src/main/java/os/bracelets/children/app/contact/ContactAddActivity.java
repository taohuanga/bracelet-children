package os.bracelets.children.app.contact;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import aio.health2world.glide_transformations.CropCircleTransformation;
import aio.health2world.http.HttpResult;
import aio.health2world.pickeview.OptionsPickerView;
import aio.health2world.rx.rxpermissions.RxPermissions;
import aio.health2world.utils.FilePathUtil;
import aio.health2world.utils.TimePickerUtil;
import aio.health2world.utils.ToastUtil;
import aio.health2world.view.LoadingDialog;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.app.personal.InputMsgActivity;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.ImageBase64;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;
import rx.functions.Action1;

public class ContactAddActivity extends BaseActivity implements OptionsPickerView.OnOptionsSelectListener {

    public static final int ITEM_HEAD = 0x01;
    public static final int ITEM_NICK = 0x02;
    public static final int ITEM_PHONE = 0x08;

    private LinearLayout layoutHeadImg, layoutNickName, layoutSex, layoutPhone;
    private TextView tvNickName, tvSex, tvPhone;
    private ImageView ivHeadImg;
    private Button btnSave;
    private FamilyMember member;
    private TitleBar titleBar;
    private RxPermissions rxPermissions;
    private OptionsPickerView optionsPicker;
    private List<String> listSex = new ArrayList<>();
    private LoadingDialog dialog;
    private String imagePath = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_add;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        layoutHeadImg = findView(R.id.layoutHeadImg);
        layoutNickName = findView(R.id.layoutNickName);
        layoutSex = findView(R.id.layoutSex);
        layoutPhone = findView(R.id.layoutPhone);
        tvNickName = findView(R.id.tvNickName);
        tvSex = findView(R.id.tvSex);
        tvPhone = findView(R.id.tvPhone);
        ivHeadImg = findView(R.id.ivHeadImg);
        btnSave = findView(R.id.btnSave);
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");

        TitleBarUtil.setAttr(this, "", "添加联系人", titleBar);
        rxPermissions = new RxPermissions(this);
        optionsPicker = TimePickerUtil.initOptions(this, this);
        listSex.add("男");
        listSex.add("女");
        optionsPicker.setPicker(listSex);

        dialog = new LoadingDialog(this);
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        tvSex.setText(listSex.get(options1));
    }

    @Override
    protected void initListener() {
        layoutHeadImg.setOnClickListener(this);
        layoutNickName.setOnClickListener(this);
        layoutSex.setOnClickListener(this);
        layoutPhone.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layoutHeadImg:
                //修改头像
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                                    // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
                                    startActivityForResult(intent, ITEM_HEAD);
                                } else {
                                    ToastUtil.showShort("相关权限被拒绝");
                                }
                            }
                        });
                break;
            case R.id.layoutSex:
                //修改性别
                optionsPicker.show();
                break;
            case R.id.layoutNickName:
                //修改昵称
                Intent intentNick = new Intent(this, InputMsgActivity.class);
                intentNick.putExtra(InputMsgActivity.KEY, "填写昵称");
                startActivityForResult(intentNick, ITEM_NICK);
                break;
            case R.id.layoutPhone:
                //修改手机号
                Intent intentPhone = new Intent(this, InputMsgActivity.class);
                intentPhone.putExtra(InputMsgActivity.KEY, "填写手机号");
                intentPhone.putExtra(InputMsgActivity.TYPE, ITEM_PHONE);
                startActivityForResult(intentPhone, ITEM_PHONE);
                break;
            case R.id.btnSave:
                addContact();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case ITEM_HEAD:
                Uri uri = data.getData();
                if (uri != null) {
                    imagePath = FilePathUtil.getRealPathFromURI(ContactAddActivity.this, uri);
                    if (!TextUtils.isEmpty(imagePath))
                        Glide.with(this)
                                .load(imagePath)
                                .placeholder(R.mipmap.ic_default_portrait)
                                .error(R.mipmap.ic_default_portrait)
                                .bitmapTransform(new CropCircleTransformation(mContext))
                                .into(ivHeadImg);
                }
                break;
            case ITEM_NICK:
                tvNickName.setText(data.getStringExtra("data"));
                break;
            case ITEM_PHONE:
                tvPhone.setText(data.getStringExtra("data"));
                break;
        }
    }

    private void addContact() {
        if (TextUtils.isEmpty(imagePath)) {
            ToastUtil.showShort("请选择图片");
            return;
        }

        String nickName = tvNickName.getText().toString();

        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.showShort("请填写昵称");
            return;
        }

        String phone = tvPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort("请填写手机号");
            return;
        }

        String sex = tvSex.getText().toString();

        if (TextUtils.isEmpty(sex)) {
            ToastUtil.showShort("请选择性别");
            return;
        }
        uploadImage();
    }

    private void uploadImage() {
        File file = new File(imagePath);
        if (!file.exists())
            return;
        String imageKey = ImageBase64.imageConvertBase64(imagePath);
        ApiRequest.uploadImage(1, imageKey, new HttpSubscriber() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dialog.dismiss();
            }

            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (result.code.equals(AppConfig.SUCCESS)) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        String serverPath = object.optString("data");
                        saveContact(serverPath);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showShort(result.errorMessage);
                }
            }
        });
    }

    private void saveContact(String serverPath) {
        ApiRequest.contactAdd(String.valueOf(member.getAccountId()), serverPath, tvNickName.getText().toString(),
                tvPhone.getText().toString(), tvSex.getText().toString().equals("男") ? 1 : 2, new HttpSubscriber() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(HttpResult result) {
                        super.onNext(result);
                        dialog.dismiss();
                        if (result.code.equals(AppConfig.SUCCESS)) {
                            ToastUtil.showShort("添加成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
    }
}
