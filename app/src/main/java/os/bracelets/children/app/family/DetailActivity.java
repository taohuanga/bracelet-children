package os.bracelets.children.app.family;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import os.bracelets.children.R;
import os.bracelets.children.app.personal.InputMsgActivity;
import os.bracelets.children.app.personal.UpdatePhoneActivity;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.common.MsgEvent;
import os.bracelets.children.utils.AppUtils;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;
import rx.functions.Action1;

import static os.bracelets.children.app.family.FamilyListFragment.REQUEST_FAMILY_CHANGED;

public class DetailActivity extends MVPBaseActivity<DetailContract.Presenter> implements DetailContract.View {
    public static final int ITEM_HEAD = 0x01;
    public static final int ITEM_NICK = 0x02;
    public static final int ITEM_NAME = 0x03;
    public static final int ITEM_SEX = 0x04;
    public static final int ITEM_RELATION = 0x05;
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

    private Button btnSave,btnDelete;

    private RxPermissions rxPermissions;

    private TimePickerView pickerView;
    //    private OptionsPickerView relationPicker;
    private OptionsPickerView sexPicker;

    private List<String> listSex = new ArrayList<>();
    private List<String> listRelation = new ArrayList<>();

    private String localImagePath;

    private String serverImageUrl;

    private String  relationShipId;

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
        btnDelete = findView(R.id.btnDelete);

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
        TitleBarUtil.setAttr(this, "", getString(R.string.relative_detail), titleBar);
        rxPermissions = new RxPermissions(this);


        pickerView = TimePickerUtil.init(this, new TimeSelectListener());
        sexPicker = TimePickerUtil.initOptions(this, new SexSelectListener());
//        relationPicker = TimePickerUtil.initOptions(this, new RelationSelectListener());
        listSex.add("男");
        listSex.add("女");
        sexPicker.setPicker(listSex);

//        listRelation.add("父子");
//        listRelation.add("母子");
//        listRelation.add("父女");
//        listRelation.add("母女");
//        relationPicker.setPicker(listRelation);

        accountId = getIntent().getStringExtra("accountId");
        relationShipId = getIntent().getStringExtra("relationShipId");
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
        btnDelete.setOnClickListener(this);
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
        tvSex.setText(AppUtils.getSex(this,member.getSex()));
        tvRelation.setText(member.getRelationship());
        tvBirthday.setText(member.getBirthday());
        tvWeight.setText(member.getWeight());
        tvHeight.setText(member.getHeight());
        tvPhone.setText(member.getPhone());
        serverImageUrl = member.getProfile();
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
            case ITEM_RELATION:
                tvRelation.setText(data.getStringExtra("data"));
                break;
        }
    }

    @Override
    public void uploadImageSuccess(String serverImagePath) {
        serverImageUrl = serverImagePath;
        updateMsg();
    }

    @Override
    public void updateMsgSuccess() {
        ToastUtil.showShort(getString(R.string.action_success));
        EventBus.getDefault().post(new MsgEvent<>(REQUEST_FAMILY_CHANGED));
        setResult(RESULT_OK);
        finish();
    }

    private void updateMsg() {
        String nickName = tvNickName.getText().toString().trim();
        String name = tvName.getText().toString();
        //0未知 1男 2女
        String sex = tvSex.getText().toString().trim();
        int sexType = 0;
        if (sex.equals(getString(R.string.man))) {
            sexType = 1;
        } else if (sex.equals(getString(R.string.woman))) {
            sexType = 2;
        }
        String birthday = tvBirthday.getText().toString();
        String height = tvHeight.getText().toString();
        String weight = tvWeight.getText().toString();
        String relation = tvRelation.getText().toString();
        String phone = tvPhone.getText().toString();
        mPresenter.updateMsg(accountId, serverImageUrl, nickName, name, sexType, birthday, height, weight, relation, phone);
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
                                    ToastUtil.showShort(getString(R.string.permission_denied));
                                }
                            }
                        });
                break;
            case R.id.layoutNickName:
                //修改昵称
                Intent intentNick = new Intent(this, InputMsgActivity.class);
                intentNick.putExtra(InputMsgActivity.KEY, getString(R.string.update_nickname));
                intentNick.putExtra(InputMsgActivity.TYPE, ITEM_NICK);
                startActivityForResult(intentNick, ITEM_NICK);
                break;
            case R.id.layoutName:
                //修改真实姓名
                Intent intentName = new Intent(this, InputMsgActivity.class);
                intentName.putExtra(InputMsgActivity.KEY, getString(R.string.update_name));
                intentName.putExtra(InputMsgActivity.TYPE, ITEM_NAME);
                startActivityForResult(intentName, ITEM_NAME);
                break;
            case R.id.layoutSex:
                //修改性别
                sexPicker.show();
                break;
            case R.id.layoutBirthday:
                //修改生日
                pickerView.show();
                break;
            case R.id.layoutRelation:
                Intent intentRelation = new Intent(this, InputMsgActivity.class);
                intentRelation.putExtra(InputMsgActivity.KEY, getString(R.string.input_relation));
                intentRelation.putExtra(InputMsgActivity.TYPE, ITEM_RELATION);
                startActivityForResult(intentRelation, ITEM_RELATION);
                break;
            case R.id.layoutHeight:
                //修改身高
                Intent intentHeight = new Intent(this, InputMsgActivity.class);
                intentHeight.putExtra(InputMsgActivity.KEY, getString(R.string.update_height));
                intentHeight.putExtra(InputMsgActivity.TYPE, ITEM_HEIGHT);
                startActivityForResult(intentHeight, ITEM_HEIGHT);
                break;
            case R.id.layoutWeight:
                //修改体重
                Intent intentWeight = new Intent(this, InputMsgActivity.class);
                intentWeight.putExtra(InputMsgActivity.KEY, getString(R.string.update_weight));
                intentWeight.putExtra(InputMsgActivity.TYPE, ITEM_WEIGHT);
                startActivityForResult(intentWeight, ITEM_WEIGHT);
                break;
            case R.id.layoutPhone:
                //修改手机号
//                ToastUtil.showShort("手机号码不支持修改");
                Intent intentPhone = new Intent(this, InputMsgActivity.class);
                intentPhone.putExtra(InputMsgActivity.KEY, R.string.update_phone_number);
                intentPhone.putExtra(InputMsgActivity.TYPE, ITEM_PHONE);
//                Intent intentPhone = new Intent(this, UpdatePhoneActivity.class);
//                String phone = tvPhone.getText().toString().trim();
//                if (!TextUtils.isEmpty(phone)) {
//                    intentPhone.putExtra("oldPhone", phone);
//                }
//                intentPhone.putExtra("accountId",accountId);
                startActivityForResult(intentPhone, ITEM_PHONE);
                break;
            case R.id.btnSave:
                if (checkData()) {
                    if (!TextUtils.isEmpty(localImagePath)) {
                        mPresenter.uploadImage(localImagePath);
                    } else {
                        updateMsg();
                    }
                }
                break;
            case R.id.btnDelete:
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.is_delete_relation))
                        .setNegativeButton(getString(R.string.pickerview_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.delFamilyMember(relationShipId);
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }

    @Override
    public void deleteSuccess() {
        ToastUtil.showShort(getString(R.string.action_success));
        EventBus.getDefault().post(new MsgEvent<>(REQUEST_FAMILY_CHANGED));
        setResult(RESULT_OK);
        finish();
    }

    private class SexSelectListener implements OptionsPickerView.OnOptionsSelectListener {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            tvSex.setText(listSex.get(options1));
        }
    }

    private class RelationSelectListener implements OptionsPickerView.OnOptionsSelectListener {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            tvRelation.setText(listRelation.get(options1));
        }
    }

    private class TimeSelectListener implements TimePickerView.OnTimeSelectListener {
        @Override
        public void onTimeSelect(Date date, View v) {
            String time = DateUtil.getTime(date);
            tvBirthday.setText(time);
        }
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(serverImageUrl) && TextUtils.isEmpty(localImagePath)) {
            ToastUtil.showShort(getString(R.string.please_upload_head_portrait));
            return false;
        }
        String nickName = tvNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.showShort(getString(R.string.nick_name_not_null));
            return false;
        }
        String name = tvName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(getString(R.string.name_not_null));
            return false;
        }
        //0未知 1男 2女
        String sex = tvSex.getText().toString().trim();
        if (TextUtils.isEmpty(sex)) {
            ToastUtil.showShort(getString(R.string.sex_not_null));
            return false;
        }
        return true;
    }
}

