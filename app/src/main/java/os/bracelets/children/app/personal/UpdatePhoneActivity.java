package os.bracelets.children.app.personal;

import android.view.View;

import os.bracelets.children.R;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/2/20.
 */

public class UpdatePhoneActivity extends MVPBaseActivity<UpdatePhoneContract.Presenter> implements UpdatePhoneContract.View {

    private TitleBar titleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", "修改手机号", titleBar);
    }

    @Override
    protected UpdatePhoneContract.Presenter getPresenter() {
        return new UpdatePhonePresenter(this);
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
