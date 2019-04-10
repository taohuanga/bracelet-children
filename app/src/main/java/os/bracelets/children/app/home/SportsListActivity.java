package os.bracelets.children.app.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import os.bracelets.children.R;
import os.bracelets.children.bean.DailySports;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class SportsListActivity extends MVPBaseActivity<SportsContract.Presenter> implements SportsContract.View {

    private TitleBar titleBar;

    private FamilyMember member;

    private RecyclerView recyclerView;

    private List<DailySports> sportsList;

    private SportsListAdapter listAdapter;

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected SportsContract.Presenter getPresenter() {
        return new SportsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "更多数据", titleBar);
        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout = findView(R.id.refreshLayout);
        refreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");

        sportsList = new ArrayList<>();
        listAdapter = new SportsListAdapter(sportsList);
        recyclerView.setAdapter(listAdapter);
        listAdapter.bindToRecyclerView(recyclerView);
        listAdapter.setEmptyView(R.layout.layout_empty_view);
        mPresenter.dailySports(String.valueOf(member.getAccountId()));
    }


    @Override
    public void dailySportsSuccess(List<DailySports> dailySportsList) {
        this.sportsList.clear();
        this.sportsList.addAll(dailySportsList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void dailySportsError() {

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
