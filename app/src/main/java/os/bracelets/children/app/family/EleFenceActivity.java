package os.bracelets.children.app.family;

import java.util.List;

import os.bracelets.children.R;
import os.bracelets.children.bean.EleFence;
import os.bracelets.children.common.MVPBaseActivity;

public class EleFenceActivity extends MVPBaseActivity<EleFenceContract.Presenter> implements EleFenceContract.View {

    @Override
    protected EleFenceContract.Presenter getPresenter() {
        return new EleFencePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initView() {
        mPresenter.eleFenceList("201");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void loadEleFenceSuccess(List<EleFence> list) {

    }
}
