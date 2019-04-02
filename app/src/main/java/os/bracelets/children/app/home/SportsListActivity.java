package os.bracelets.children.app.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class SportsListActivity extends MVPBaseActivity<SportsContract.Presenter> implements SportsContract.View {

    private TitleBar titleBar;

    private FamilyMember member;

    private RecyclerView recyclerView;

    @Override
    protected SportsContract.Presenter getPresenter() {
        return new SportsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag_list;
    }

    @Override
    protected void initView() {
        findView(R.id.btnSave).setVisibility(View.GONE);
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "更多数据", titleBar);
        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");
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
