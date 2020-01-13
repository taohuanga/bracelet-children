package os.bracelets.children.app.about;

import android.view.View;
import android.widget.TextView;

import os.bracelets.children.BuildConfig;
import os.bracelets.children.R;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/2/20.
 */

public class AboutActivity extends BaseActivity {

    private TitleBar titleBar;

    private TextView tvContent,tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        tvContent = findView(R.id.tvContent);
        tvTitle = findView(R.id.tvTitle);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", getString(R.string.about_us), titleBar);
        tvTitle.setText(getString(R.string.app_name)+"V" + BuildConfig.VERSION_NAME + "_" + BuildConfig.GIT_VERSION);
        tvContent.setText("\r\r\r\r\r\r\r\r" + getResources().getString(R.string.about_content));
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
}
