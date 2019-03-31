package os.bracelets.children.app.edit;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.Logger;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class EditRemindActivity extends BaseActivity {

    private TitleBar titleBar;
    private FamilyMember member;
    private EditText edTitle, edContent;
    private TextView tvCycle, tvTime;
    private Button btnSave;

    private LinearLayout llRemindTime, llRemindCycle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_remind;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "设置提醒", titleBar);
        edTitle = findView(R.id.edTitle);
        edContent = findView(R.id.edContent);
        tvCycle = findView(R.id.tvCycle);
        tvTime = findView(R.id.tvTime);
        btnSave = findView(R.id.btnSave);
        llRemindTime = findView(R.id.llRemindTime);
        llRemindCycle = findView(R.id.llRemindCycle);
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");
        Logger.i("member", member.toString());
    }

    @Override
    protected void initListener() {
        btnSave.setOnClickListener(this);
        llRemindCycle.setOnClickListener(this);
        llRemindTime.setOnClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                remind();
                break;
            case R.id.llRemindCycle:
                break;
            case R.id.llRemindTime:
                break;
        }
    }

    private void remind() {
        String title = edTitle.getText().toString();
        String content = edContent.getText().toString();
        String cycle = tvCycle.getText().toString();
        String time = tvTime.getText().toString();

        if (TextUtils.isEmpty(title)) {
            ToastUtil.showShort("标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showShort("内容不能为空");
            return;
        }
//        if (TextUtils.isEmpty(cycle)) {
//            ToastUtil.showShort("请设置提醒周期");
//            return;
//        }
//
//        if (TextUtils.isEmpty(time)) {
//            ToastUtil.showShort("请设置提醒时间");
//            return;
//        }

        ApiRequest.remind(String.valueOf(member.getAccountId()), title, content, "1;2;3;4;5;6;7",
                "00-30-00", new HttpSubscriber() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(HttpResult result) {
                        super.onNext(result);
                        if (result.code.equals(AppConfig.SUCCESS)) {
                            ToastUtil.showShort("操作成功");
                        }
                        EditRemindActivity.this.finish();
                    }
                });

    }
}
