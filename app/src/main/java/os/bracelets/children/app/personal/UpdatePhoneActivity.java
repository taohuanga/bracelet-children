package os.bracelets.children.app.personal;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        TitleBarUtil.setAttr(this, "", getString(R.string.update_phone_number), titleBar);
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
                    ToastUtil.showShort(getString(R.string.input_old_phone));
                    return;
                }

                if(phone.length()!=11){
                    ToastUtil.showShort(getString(R.string.phone_incorrect));
                    return;
                }
//                if (!MatchUtil.isPhoneLegal(phone)) {
//                    ToastUtil.showShort(getString(R.string.phone_incorrect));
//                    return;
//                }
                String newPhone = edNewPhone.getText().toString().trim();
                if (TextUtils.isEmpty(newPhone)) {
                    ToastUtil.showShort(getString(R.string.input_new_phone));
                    return;
                }

                if(newPhone.length()!=11){
                    ToastUtil.showShort(getString(R.string.phone_incorrect));
                    return;
                }

//                if (!MatchUtil.isPhoneLegal(newPhone)) {
//                    ToastUtil.showShort(getString(R.string.phone_incorrect));
//                    return;
//                }
                String code = edCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShort(getString(R.string.input_code));
                    return;
                }
                String pwd = edPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showShort(getString(R.string.input_login_password));
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
//            tvCode.setText(millisUntilFinished / 1000 + "秒后获取");
            tvCode.setText(String.format(getString(R.string.code_later),millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            tvCode.setEnabled(true);
            tvCode.setTextColor(mContext.getResources().getColor(R.color.blue));
            tvCode.setText(getString(R.string.verification_code));
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
        ToastUtil.showShort(getString(R.string.action_success));
        this.finish();
    }

    /**
     * 获取短信验证码
     */
    private void getCode() {
        String phone = edOldPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(getString(R.string.input_old_phone));
            return;
        }
        if(phone.length()!=11){
            ToastUtil.showShort(getString(R.string.phone_incorrect));
            return;
        }
//        if (!MatchUtil.isPhoneLegal(phone)) {
//            ToastUtil.showShort(getString(R.string.phone_incorrect));
//            return;
//        }
        String newPhone = edNewPhone.getText().toString().trim();
        if (TextUtils.isEmpty(newPhone)) {
            ToastUtil.showShort(getString(R.string.input_new_phone));
            return;
        }
        if(phone.length()!=11){
            ToastUtil.showShort(getString(R.string.phone_incorrect));
            return;
        }
//        if (!MatchUtil.isPhoneLegal(newPhone)) {
//            ToastUtil.showShort(getString(R.string.phone_incorrect));
//            return;
//        }
        mPresenter.securityCode(3, newPhone);
    }
}
