package os.bracelets.children.app.family;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aio.health2world.glide_transformations.CropCircleTransformation;
import aio.health2world.pickeview.OptionsPickerView;
import aio.health2world.pickeview.TimePickerView;
import aio.health2world.rx.rxpermissions.RxPermissions;
import aio.health2world.utils.DateUtil;
import aio.health2world.utils.FilePathUtil;
import aio.health2world.utils.TimePickerUtil;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.app.personal.InputMsgActivity;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.common.MsgEvent;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;
import rx.functions.Action1;

/**
 * Created by lishiyou on 2019/3/26.
 */

public class FamilyAddActivity extends MVPBaseActivity<FamilyAddContract.Presenter>
        implements FamilyAddContract.View, TimePickerView.OnTimeSelectListener {

    public static final int ITEM_HEAD = 0x01;
    public static final int ITEM_NICK = 0x02;
    public static final int ITEM_NAME = 0x03;
    public static final int ITEM_SEX = 0x04;
    public static final int ITEM_BIRTHDAY = 0x05;
    public static final int ITEM_WEIGHT = 0x06;
    public static final int ITEM_HEIGHT = 0x07;
    public static final int ITEM_PHONE = 0x08;

    private TitleBar titleBar;

    private View layoutHeadImg, layoutNickName, layoutName, layoutSex, layoutBirthday, layoutWeight,
            layoutHeight, layoutPhone, layoutRelation;

    private TextView tvNickName, tvName, tvSex, tvBirthday, tvWeight, tvHeight, tvPhone, tvRelation;

    private ImageView ivHeadImg;

    private Button btnSave;

    private RxPermissions rxPermissions;

    private TimePickerView pickerView;

    private OptionsPickerView optionsSex, optionsRelation;

    private List<String> listSex = new ArrayList<>();
    private List<String> listRelation = new ArrayList<>();

    private String localImagePath = "";

    @Override
    protected FamilyAddContract.Presenter getPresenter() {
        return new FamilyAddPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_add;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        btnSave = findView(R.id.btnSave);

        ivHeadImg = findView(R.id.ivHeadImg);
        tvNickName = findView(R.id.tvNickName);
        tvName = findView(R.id.tvName);
        tvSex = findView(R.id.tvSex);
        tvBirthday = findView(R.id.tvBirthday);
        tvWeight = findView(R.id.tvWeight);
        tvHeight = findView(R.id.tvHeight);
        tvPhone = findView(R.id.tvPhone);
        tvRelation = findView(R.id.tvRelation);

        layoutHeadImg = findView(R.id.layoutHeadImg);
        layoutNickName = findView(R.id.layoutNickName);
        layoutName = findView(R.id.layoutName);
        layoutSex = findView(R.id.layoutSex);
        layoutBirthday = findView(R.id.layoutBirthday);
        layoutWeight = findView(R.id.layoutWeight);
        layoutHeight = findView(R.id.layoutHeight);
        layoutPhone = findView(R.id.layoutPhone);
        layoutRelation = findView(R.id.layoutRelation);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", "添加亲人", titleBar);
        rxPermissions = new RxPermissions(this);

        pickerView = TimePickerUtil.init(this, this);
        optionsSex = TimePickerUtil.initOptions(this, new OptionsSex());
        listSex.add("男");
        listSex.add("女");
        optionsSex.setPicker(listSex);

        optionsRelation = TimePickerUtil.initOptions(this, new OptionsRelation());
        listRelation.add("父亲");
        listRelation.add("母亲");
        optionsRelation.setPicker(listRelation);
    }


    class OptionsSex implements OptionsPickerView.OnOptionsSelectListener {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            tvSex.setText(listSex.get(options1));
        }
    }


    class OptionsRelation implements OptionsPickerView.OnOptionsSelectListener {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            tvRelation.setText(listRelation.get(options1));
        }
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        String time = DateUtil.getTime(date);
        tvBirthday.setText(time);
    }

    @Override
    protected void initListener() {
        setOnClickListener(layoutHeadImg);
        setOnClickListener(layoutNickName);
        setOnClickListener(layoutName);
        setOnClickListener(layoutSex);
        setOnClickListener(layoutBirthday);
        setOnClickListener(layoutWeight);
        setOnClickListener(layoutHeight);
        setOnClickListener(layoutPhone);
        setOnClickListener(layoutRelation);
        setOnClickListener(btnSave);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void uploadImageSuccess(String imageUrl) {
        uploadMsg(imageUrl);
    }

    @Override
    public void addMemberSuccess() {
        setResult(RESULT_OK);
        finish();
        EventBus.getDefault().post(new MsgEvent<>(AppConfig.MSG_FAMILY_MEMBER));
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
            case R.id.layoutNickName:
                //填写昵称
                Intent intentNick = new Intent(this, InputMsgActivity.class);
                intentNick.putExtra(InputMsgActivity.KEY, "填写昵称");
                startActivityForResult(intentNick, ITEM_NICK);
                break;
            case R.id.layoutName:
                //填写真实姓名
                Intent intentName = new Intent(this, InputMsgActivity.class);
                intentName.putExtra(InputMsgActivity.KEY, "填写姓名");
                startActivityForResult(intentName, ITEM_NAME);
                break;
            case R.id.layoutSex:
                //填写性别
                optionsSex.show();
                break;
            case R.id.layoutBirthday:
                //填写生日
                pickerView.show();
                break;
            case R.id.layoutHeight:
                //填写身高
                Intent intentHeight = new Intent(this, InputMsgActivity.class);
                intentHeight.putExtra(InputMsgActivity.KEY, "填写身高");
                intentHeight.putExtra(InputMsgActivity.TYPE, ITEM_HEIGHT);
                startActivityForResult(intentHeight, ITEM_HEIGHT);
                break;
            case R.id.layoutWeight:
                //填写体重
                Intent intentWeight = new Intent(this, InputMsgActivity.class);
                intentWeight.putExtra(InputMsgActivity.KEY, "填写体重");
                intentWeight.putExtra(InputMsgActivity.TYPE, ITEM_WEIGHT);
                startActivityForResult(intentWeight, ITEM_WEIGHT);
                break;
            case R.id.layoutPhone:
                //填写手机号
                Intent intentPhone = new Intent(this, InputMsgActivity.class);
                intentPhone.putExtra(InputMsgActivity.KEY, "填写手机号");
                intentPhone.putExtra(InputMsgActivity.TYPE, ITEM_PHONE);
                startActivityForResult(intentPhone, ITEM_PHONE);
                break;
            case R.id.layoutRelation:
                optionsRelation.show();
                //填写关系
//                Intent intentAddress = new Intent(this, InputMsgActivity.class);
//                intentAddress.putExtra(InputMsgActivity.KEY, "修改住址");
//                startActivityForResult(intentAddress, ITEM_ADDRESS);
                break;
            case R.id.btnSave:
                if (checkData())
                    mPresenter.uploadImage(localImagePath);
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
                    String imagePath = FilePathUtil.getRealPathFromURI(FamilyAddActivity.this, uri);
                    if (!TextUtils.isEmpty(imagePath))
                        Glide.with(this)
                                .load(imagePath)
                                .placeholder(R.mipmap.ic_default_portrait)
                                .error(R.mipmap.ic_default_portrait)
                                .bitmapTransform(new CropCircleTransformation(mContext))
                                .into(ivHeadImg);
                    localImagePath = imagePath;
                }

                break;
            case ITEM_NICK:
                tvNickName.setText(data.getStringExtra("data"));
                break;
            case ITEM_NAME:
                tvName.setText(data.getStringExtra("data"));
                break;
            case ITEM_WEIGHT:
                tvWeight.setText(data.getStringExtra("data"));
                break;
            case ITEM_HEIGHT:
                tvHeight.setText(data.getStringExtra("data"));
                break;
            case ITEM_PHONE:
                tvPhone.setText(data.getStringExtra("data"));
                break;
        }
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(localImagePath)) {
            ToastUtil.showShort("请选择图片");
            return false;
        }

        if (TextUtils.isEmpty(tvNickName.getText())) {
            ToastUtil.showShort("请填写昵称");
            return false;
        }
        if (TextUtils.isEmpty(tvName.getText())) {
            ToastUtil.showShort("请填写姓名");
            return false;
        }
        if (TextUtils.isEmpty(tvSex.getText())) {
            ToastUtil.showShort("请选择性别");
            return false;
        }
        if (TextUtils.isEmpty(tvRelation.getText())) {
            ToastUtil.showShort("请选择关系");
            return false;
        }

        if (TextUtils.isEmpty(tvBirthday.getText())) {
            ToastUtil.showShort("请选择出生日期");
            return false;
        }
        if (TextUtils.isEmpty(tvHeight.getText())) {
            ToastUtil.showShort("请填写身高");
            return false;
        }
        if (TextUtils.isEmpty(tvWeight.getText())) {
            ToastUtil.showShort("请填写体重");
            return false;
        }
        if (TextUtils.isEmpty(tvPhone.getText())) {
            ToastUtil.showShort("请填写手机号");
            return false;
        }
        return true;
    }

    private void uploadMsg(String serverPath) {
        String nickName = tvNickName.getText().toString();
        String name = tvName.getText().toString();
        int sex = tvSex.getText().equals("男") ? 1 : 2;
        String relation = tvRelation.getText().toString();
        String birthday = tvBirthday.getText().toString();
        String height = tvHeight.getText().toString();
        String weight = tvWeight.getText().toString();
        String phone = tvPhone.getText().toString();
        mPresenter.addFamilyMember(serverPath, nickName, name, sex, birthday, height, weight, relation, phone);
    }
}
