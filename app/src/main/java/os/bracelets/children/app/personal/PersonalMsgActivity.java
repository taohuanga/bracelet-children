package os.bracelets.children.app.personal;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
import os.bracelets.children.R;
import os.bracelets.children.bean.UserInfo;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.AppUtils;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;
import rx.functions.Action1;

/**
 * Created by lishiyou on 2019/2/23.
 */

public class PersonalMsgActivity extends MVPBaseActivity<PersonalMsgContract.Presenter>
        implements PersonalMsgContract.View, TimePickerView.OnTimeSelectListener,
        OptionsPickerView.OnOptionsSelectListener {

    private String localImagePath;

    private String serverImageUrl;

    public static final int ITEM_HEAD = 0x01;
    public static final int ITEM_NICK = 0x02;
    public static final int ITEM_NAME = 0x03;
    public static final int ITEM_SEX = 0x04;
    public static final int ITEM_RELATION = 0x05;
    public static final int ITEM_WEIGHT = 0x06;
    public static final int ITEM_HEIGHT = 0x07;
    public static final int ITEM_PHONE = 0x08;
    public static final int ITEM_ADDRESS = 0x09;

    private TitleBar titleBar;

    private View layoutHeadImg, layoutNickName, layoutName, layoutSex, layoutBirthday, layoutWeight,
            layoutHeight, layoutPhone, layoutHomeAddress;

    private TextView tvNickName, tvName, tvSex, tvBirthday, tvWeight, tvHeight, tvPhone, tvHomeAddress;

    private ImageView ivHeadImg;

    private RxPermissions rxPermissions;

    private TimePickerView pickerView;

    private OptionsPickerView optionsPicker;

    private List<String> listSex = new ArrayList<>();

    @Override
    protected PersonalMsgContract.Presenter getPresenter() {
        return new PersonalMsgPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_msg;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);

        ivHeadImg = findView(R.id.ivHeadImg);
        tvNickName = findView(R.id.tvNickName);
        tvName = findView(R.id.tvName);
        tvSex = findView(R.id.tvSex);
        tvBirthday = findView(R.id.tvBirthday);
        tvWeight = findView(R.id.tvWeight);
        tvHeight = findView(R.id.tvHeight);
        tvPhone = findView(R.id.tvPhone);
        tvHomeAddress = findView(R.id.tvHomeAddress);

        layoutHeadImg = findView(R.id.layoutHeadImg);
        layoutNickName = findView(R.id.layoutNickName);
        layoutName = findView(R.id.layoutName);
        layoutSex = findView(R.id.layoutSex);
        layoutBirthday = findView(R.id.layoutBirthday);
        layoutWeight = findView(R.id.layoutWeight);
        layoutHeight = findView(R.id.layoutHeight);
        layoutPhone = findView(R.id.layoutPhone);
        layoutHomeAddress = findView(R.id.layoutHomeAddress);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", "修改资料", titleBar);
        mPresenter.userInfo();
        rxPermissions = new RxPermissions(this);
        pickerView = TimePickerUtil.init(this, this);
        optionsPicker = TimePickerUtil.initOptions(this, this);
        listSex.add("男");
        listSex.add("女");
        optionsPicker.setPicker(listSex);
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
        setOnClickListener(layoutHomeAddress);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.addAction(new TitleBar.TextAction("保存") {
            @Override
            public void performAction(View view) {
                saveMsg();
            }
        });
    }


    @Override
    public void onTimeSelect(Date date, View v) {
        String time = DateUtil.getTime(date);
        tvBirthday.setText(time);
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        tvSex.setText(listSex.get(options1));
    }

    @Override
    public void loadInfoSuccess(UserInfo info) {
        serverImageUrl = info.getPortrait();
        Glide.with(this)
                .load(info.getPortrait())
                .placeholder(R.mipmap.ic_default_portrait)
                .error(R.mipmap.ic_default_portrait)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(ivHeadImg);

        tvNickName.setText(info.getNickName());
        tvName.setText(info.getName());
        tvSex.setText(AppUtils.getSex(info.getSex()));
        tvBirthday.setText(info.getBirthday());
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
                //修改昵称
                Intent intentNick = new Intent(this, InputMsgActivity.class);
                intentNick.putExtra(InputMsgActivity.KEY, "修改昵称");
                startActivityForResult(intentNick, ITEM_NICK);
                break;
            case R.id.layoutName:
                //修改真实姓名
                Intent intentName = new Intent(this, InputMsgActivity.class);
                intentName.putExtra(InputMsgActivity.KEY, "修改姓名");
//                intentName.putExtra(InputMsgActivity.TYPE, ITEM_NAME);
                startActivityForResult(intentName, ITEM_NAME);
                break;
            case R.id.layoutSex:
                //修改性别
                optionsPicker.show();
                break;
            case R.id.layoutBirthday:
                //修改生日
                pickerView.show();
                break;
            case R.id.layoutHeight:
                //修改身高
                Intent intentHeight = new Intent(this, InputMsgActivity.class);
                intentHeight.putExtra(InputMsgActivity.KEY, "修改身高");
                intentHeight.putExtra(InputMsgActivity.TYPE, ITEM_HEIGHT);
                startActivityForResult(intentHeight, ITEM_HEIGHT);
                break;
            case R.id.layoutWeight:
                //修改体重
                Intent intentWeight = new Intent(this, InputMsgActivity.class);
                intentWeight.putExtra(InputMsgActivity.KEY, "修改体重");
                intentWeight.putExtra(InputMsgActivity.TYPE, ITEM_WEIGHT);
                startActivityForResult(intentWeight, ITEM_WEIGHT);
                break;
            case R.id.layoutPhone:
                //修改手机号
                Intent intentPhone = new Intent(this, UpdatePhoneActivity.class);
                startActivityForResult(intentPhone, ITEM_PHONE);
                break;
            case R.id.layoutHomeAddress:
                //修改家庭住址
                Intent intentAddress = new Intent(this, InputMsgActivity.class);
                intentAddress.putExtra(InputMsgActivity.KEY, "修改住址");
                startActivityForResult(intentAddress, ITEM_ADDRESS);
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
                    localImagePath = FilePathUtil.getRealPathFromURI(PersonalMsgActivity.this, uri);
                    if (!TextUtils.isEmpty(localImagePath))
                        Glide.with(this)
                                .load(localImagePath)
                                .placeholder(R.mipmap.ic_default_portrait)
                                .error(R.mipmap.ic_default_portrait)
                                .bitmapTransform(new CropCircleTransformation(mContext))
                                .into(ivHeadImg);
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
//                tvPhone.setText(data.getStringExtra("data"));
                break;
            case ITEM_ADDRESS:
                tvHomeAddress.setText(data.getStringExtra("data"));
                break;
        }
    }

    //保存资料
    private void saveMsg() {
        if (TextUtils.isEmpty(serverImageUrl) && TextUtils.isEmpty(localImagePath)) {
            ToastUtil.showShort("请先上传头像");
            return;
        }
        String nickName = tvNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.showShort("昵称不能为空");
            return;
        }
        String name = tvName.getText().toString();
//        if (TextUtils.isEmpty(name)) {
//            ToastUtil.showShort("姓名不能为空");
//            return;
//        }
        //0未知 1男 2女
        String sex = tvSex.getText().toString().trim();
        if (TextUtils.isEmpty(sex)) {
            ToastUtil.showShort("性别不能为空");
            return;
        }
        int sexType = 0;
        if (sex.equals("男")) {
            sexType = 1;
        } else if (sex.equals("女")) {
            sexType = 2;
        }

        String birthday = tvBirthday.getText().toString();
        if (TextUtils.isEmpty(birthday)) {
            ToastUtil.showShort("出生日期不能为空");
            return;
        }
        if (!TextUtils.isEmpty(localImagePath))
            mPresenter.uploadImage(localImagePath);
        else
            mPresenter.modifyData(serverImageUrl, nickName, name, birthday, sexType);

    }

    @Override
    public void updateMsgSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void uploadImageSuccess(String imageUrl) {
        mPresenter.modifyData(imageUrl, tvNickName.getText().toString(), tvName.getText().toString(),
                tvBirthday.getText().toString(), tvSex.getText().equals("男") ? 1 : 2);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pickerView = null;
        optionsPicker = null;
    }
}
