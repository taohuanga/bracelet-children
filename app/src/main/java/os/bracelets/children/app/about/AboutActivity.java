package os.bracelets.children.app.about;

import android.view.View;

import os.bracelets.children.R;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/2/20.
 */

public class AboutActivity extends BaseActivity {

    private TitleBar titleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", "关于我们", titleBar);
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
