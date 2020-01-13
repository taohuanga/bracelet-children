package os.bracelets.children.app.personal;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import aio.health2world.utils.MD5Util;
import aio.health2world.utils.SPUtils;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/2/20.
 */

public class UpdatePwdActivity extends MVPBaseActivity<UpdatePwdContract.Presenter> implements
        UpdatePwdContract.View {

    private TitleBar titleBar;

    private EditText edCode, edOldPwd, edNewPwd, edRePwd;

    private Button btnOk;

    private TextView tvCode;

    private String phone;

    @Override
    protected UpdatePwdContract.Presenter getPresenter() {
        return new UpdatePwdPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        edOldPwd = findView(R.id.edOldPwd);
        edNewPwd = findView(R.id.edNewPwd);
        edRePwd = findView(R.id.edRePwd);
        edCode = findView(R.id.edCode);
        tvCode = findView(R.id.tvCode);
        btnOk = findView(R.id.btnOk);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", getString(R.string.update_password), titleBar);
    }


    @Override
    protected void initListener() {
        btnOk.setOnClickListener(this);
        tvCode.setOnClickListener(this);
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
                phone = (String) SPUtils.get(this, AppConfig.USER_PHONE, "");
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showShort(getString(R.string.phone_incorrect));
                    return;
                }
                mPresenter.code(3, phone);
                break;
            case R.id.btnOk:
                updatePwd();
                break;
        }
    }

    private void updatePwd() {
        String oldPwd = edOldPwd.getText().toString().trim();
        String newPwd = edNewPwd.getText().toString().trim();
        String rePwd = edRePwd.getText().toString().trim();
        String code = edCode.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtil.showShort(getString(R.string.input_old_password));
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtil.showShort(getString(R.string.input_new_password));
            return;
        }
        if (TextUtils.equals(oldPwd, newPwd)) {
            ToastUtil.showShort(getString(R.string.can_not_same));
            return;
        }
        if (!TextUtils.equals(newPwd, rePwd)) {
            ToastUtil.showShort(getString(R.string.new_password_not_match));
            return;
        }
        mPresenter.updatePwd(MD5Util.getMD5String(oldPwd), MD5Util.getMD5String(newPwd), code);
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
    public void codeSuccess() {
        ToastUtil.showShort(getString(R.string.send_success));
        tvCode.setEnabled(false);
        tvCode.setTextColor(mContext.getResources().getColor(R.color.black9));
        countDownTimer.start();
    }

    @Override
    public void updatePwdSuccess() {
        ToastUtil.showShort(getString(R.string.action_success));
        finish();
    }
}
