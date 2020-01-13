package os.bracelets.children.app.navigate;

import android.view.View;

import os.bracelets.children.R;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/2/24.
 */

public class NavigateActivity extends MVPBaseActivity<NavigateContract.Presenter> implements NavigateContract.View {

    private TitleBar titleBar;

    @Override
    protected NavigateContract.Presenter getPresenter() {
        return new NavigatePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigate;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", getString(R.string.navigation), titleBar);
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
