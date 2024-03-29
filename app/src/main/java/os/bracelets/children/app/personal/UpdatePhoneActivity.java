package os.bracelets.children.app.personal;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import aio.health2world.utils.MatchUtil;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.R;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/2/20.
 */

public class UpdatePhoneActivity extends MVPBaseActivity<UpdatePhoneContract.Presenter> implements UpdatePhoneContract.View {

    private TitleBar titleBar;

    private String accountId,oldPhone;

    private EditText edOldPhone, edNewPhone, edCode, edPwd;

    private TextView tvCode;

    private Button btnSubmit;

    @Override
    protected UpdatePhoneContract.Presenter getPresenter() {
        return new UpdatePhonePresenter(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        edOldPhone = findView(R.id.edOldPhone);
        edNewPhone = findView(R.id.edNewPhone);
        edCode = findView(R.id.edCode);
        edPwd = findView(R.id.edPwd);
        tvCode = findView(R.id.tvCode);
        btnSubmit = findView(R.id.btnSubmit);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", "修改手机号", titleBar);
        if (getIntent().hasExtra("oldPhone")) {
            oldPhone = getIntent().getStringExtra("oldPhone");
            edOldPhone.setText(oldPhone);
        }
        accountId = getIntent().getStringExtra("accountId");
    }

    @Override
    protected void initListener() {
        tvCode.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
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
            case R.id.tvCode:
                getCode();
                break;
            case R.id.btnSubmit:
                String phone = edOldPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showShort("请输入原手机号");
                    return;
                }
                if (!MatchUtil.isPhoneLegal(phone)) {
                    ToastUtil.showShort("原手机号格式不正确");
                    return;
                }
                String newPhone = edNewPhone.getText().toString().trim();
                if (TextUtils.isEmpty(newPhone)) {
                    ToastUtil.showShort("请输入新手机号");
                    return;
                }
                if (!MatchUtil.isPhoneLegal(newPhone)) {
                    ToastUtil.showShort("新手机号格式不正确");
                    return;
                }
                String code = edCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShort("请输入验证码");
                    return;
                }
                String pwd = edPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showShort("请输入登录密码");
                    return;
                }
                mPresenter.updatePhone(accountId,oldPhone, newPhone, code, pwd);
                break;
        }
    }

    //计时器
    private CountDownTimer countDownTimer = new CountDownTimer(59000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvCode.setText(millisUntilFinished / 1000 + "秒后获取");
        }

        @Override
        public void onFinish() {
            tvCode.setEnabled(true);
            tvCode.setTextColor(mContext.getResources().getColor(R.color.blue));
            tvCode.setText("获取验证码");
        }
    };

    @Override
    public void securityCodeSuccess() {
        tvCode.setEnabled(false);
        tvCode.setTextColor(mContext.getResources().getColor(R.color.black9));
        countDownTimer.start();
    }

    @Override
    public void updatePhoneSuccess() {
        ToastUtil.showShort("操作成功");
        this.finish();
    }

    /**
     * 获取短信验证码
     */
    private void getCode() {
        String phone = edOldPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort("请输入原手机号");
            return;
        }
        if (!MatchUtil.isPhoneLegal(phone)) {
            ToastUtil.showShort("原手机号格式不正确");
            return;
        }
        String newPhone = edNewPhone.getText().toString().trim();
        if (TextUtils.isEmpty(newPhone)) {
            ToastUtil.showShort("请输入新手机号");
            return;
        }
        if (!MatchUtil.isPhoneLegal(newPhone)) {
            ToastUtil.showShort("新手机号格式不正确");
            return;
        }
        mPresenter.securityCode(3, newPhone);
    }
}
