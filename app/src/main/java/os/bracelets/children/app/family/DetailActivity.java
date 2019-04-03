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
import os.bracelets.children.app.personal.InputMsgActivity;
import os.bracelets.children.app.personal.PersonalMsgActivity;
import os.bracelets.children.app.personal.UpdatePhoneActivity;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.AppUtils;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;
import rx.functions.Action1;

public class DetailActivity extends MVPBaseActivity<DetailContract.Presenter>
        implements DetailContract.View, TimePickerView.OnTimeSelectListener,
        OptionsPickerView.OnOptionsSelectListener {
    public static final int ITEM_HEAD = 0x01;
    public static final int ITEM_NICK = 0x02;
    public static final int ITEM_NAME = 0x03;
    public static final int ITEM_SEX = 0x04;
    public static final int ITEM_BIRTHDAY = 0x05;
    public static final int ITEM_WEIGHT = 0x06;
    public static final int ITEM_HEIGHT = 0x07;
    public static final int ITEM_PHONE = 0x08;
    public static final int ITEM_ADDRESS = 0x09;

    private String accountId;

    private TitleBar titleBar;

    private View layoutHeadImg, layoutNickName, layoutName, layoutSex, layoutBirthday, layoutWeight,
            layoutHeight, layoutPhone, layoutRelation;

    private TextView tvNickName, tvName, tvSex, tvBirthday, tvWeight, tvHeight, tvPhone, tvRelation;

    private ImageView ivHeadImg;

    private Button btnSave;

    private RxPermissions rxPermissions;
    private TimePickerView pickerView;

    private OptionsPickerView optionsPicker;

    private List<String> listSex = new ArrayList<>();

    private String localImagePath;

    private String serverImageUrl;

    @Override
    protected DetailPresenter getPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_detail;
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
        TitleBarUtil.setAttr(this, "", "亲人详情", titleBar);
        rxPermissions = new RxPermissions(this);
        pickerView = TimePickerUtil.init(this, this);
        optionsPicker = TimePickerUtil.initOptions(this, this);
        listSex.add("男");
        listSex.add("女");
        optionsPicker.setPicker(listSex);
        accountId = getIntent().getStringExtra("accountId");
        mPresenter.memberInfo(accountId);
    }

    @Override
    protected void initListener() {
        layoutHeadImg.setOnClickListener(this);
        layoutNickName.setOnClickListener(this);
        layoutName.setOnClickListener(this);
        layoutSex.setOnClickListener(this);
        layoutBirthday.setOnClickListener(this);
        layoutWeight.setOnClickListener(this);
        layoutHeight.setOnClickListener(this);
        layoutPhone.setOnClickListener(this);
        layoutRelation.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadMemberInfoSuccess(FamilyMember member) {
        tvNickName.setText(member.getNickName());
        tvName.setText(member.getRealName());
        tvSex.setText(AppUtils.getSex(member.getSex()));
        tvRelation.setText(member.getRelationship());
        tvBirthday.setText(member.getBirthday());
        tvWeight.setText(member.getWeight());
        tvHeight.setText(member.getHeight());
        tvPhone.setText(member.getPhone());

        Glide.with(this)
                .load(member.getProfile())
                .placeholder(R.mipmap.ic_default_portrait)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivHeadImg);
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
                    localImagePath = FilePathUtil.getRealPathFromURI(DetailActivity.this, uri);
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
                tvPhone.setText(data.getStringExtra("data"));
                break;
        }
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
    public void uploadImageSuccess(String serverImagePath) {

    }

    @Override
    public void updateMsgSuccess() {

    }

    @Override
    public void onClick(View v) {
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
            case R.id.layoutRelation:
                break;
            case R.id.btnSave:
                break;
        }
    }
}

