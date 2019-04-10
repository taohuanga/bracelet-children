package os.bracelets.children.app.account;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import aio.health2world.utils.SPUtils;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.app.main.MainActivity;
import os.bracelets.children.common.BaseActivity;

/**
 * Created by lishiyou on 2019/1/27.
 */

public class StartActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = (boolean) SPUtils.get(StartActivity.this, AppConfig.IS_LOGIN, false);
                if (isLogin) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 1500);

    }


    @Override
    protected void initListener() {

    }
}
