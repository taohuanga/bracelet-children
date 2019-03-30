package os.bracelets.children.app.family;

import os.bracelets.children.R;
import os.bracelets.children.common.MVPBaseActivity;

public class TagListActivity extends MVPBaseActivity<TagContract.Presenter> implements TagContract.View {


    @Override
    protected TagContract.Presenter getPresenter() {
        return new TagPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag_list;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.getTagList();
    }


    @Override
    protected void initListener() {

    }


    @Override
    public void loadTagSuccess() {

    }
}
