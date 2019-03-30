package os.bracelets.children.app.family;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import aio.health2world.glide_transformations.CropCircleTransformation;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.AppUtils;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class DetailActivity extends MVPBaseActivity<DetailContract.Presenter>
        implements DetailContract.View {

    private String accountId;

    private TitleBar titleBar;

    private View layoutHeadImg, layoutNickName, layoutName, layoutSex, layoutBirthday, layoutWeight,
            layoutHeight, layoutPhone, layoutRelation;

    private TextView tvNickName, tvName, tvSex, tvBirthday, tvWeight, tvHeight, tvPhone, tvRelation;

    private ImageView ivHeadImg;

    private Button btnSave;

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
        accountId = getIntent().getStringExtra("accountId");
        mPresenter.memberInfo(accountId);
    }

    @Override
    protected void initListener() {
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
}

