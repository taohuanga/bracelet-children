package os.bracelets.children.app.family;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.recyclerview.DividerItemDecoration;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.app.home.FallPositionActivity;
import os.bracelets.children.app.home.RemindAdapter;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class MsgListActivity extends MVPBaseActivity<MsgListContract.Presenter> implements MsgListContract.View,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private int pageIndex = 1;

    private TitleBar titleBar;

    private String accountId;

    private List<RemindBean> remindList;

    private RemindAdapter remindAdapter;

    @Override
    protected MsgListPresenter getPresenter() {
        return new MsgListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);

        TitleBarUtil.setAttr(this, "", "消息", titleBar);
        recyclerView = findView(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        refreshLayout = findView(R.id.refreshLayout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.appThemeColor));
    }

    @Override
    protected void initData() {
        accountId = getIntent().getStringExtra("accountId");
        remindList = new ArrayList<>();
        remindAdapter = new RemindAdapter(remindList);
        recyclerView.setAdapter(remindAdapter);
        remindAdapter.bindToRecyclerView(recyclerView);
        remindAdapter.setEmptyView(R.layout.layout_empty_view);

        mPresenter.msgList(pageIndex, accountId);
    }

    @Override
    protected void initListener() {
        remindAdapter.setOnItemClickListener(this);
        remindAdapter.setOnLoadMoreListener(this, recyclerView);
        refreshLayout.setOnRefreshListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadMsgSuccess(List<RemindBean> list) {
        refreshLayout.setRefreshing(false);
        if (pageIndex == 1) {
            remindList.clear();
        }
        if (list.size() >= AppConfig.PAGE_SIZE) {
            remindAdapter.loadMoreComplete();
        } else {
            remindAdapter.loadMoreEnd();
        }
        remindList.addAll(list);
        remindAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RemindBean remindBean = (RemindBean) adapter.getItem(position);
        //0 系统消息 1 跌倒消息 2 电子围栏 3 电量提示
        if (remindBean.getMsgType() == 1) {
            Intent intent = new Intent(this, FallPositionActivity.class);
//            intent.putExtra("member", familyMemberList.get(currentPos));
            intent.putExtra("remind", remindBean);
            intent.putExtra("type", 0);
            startActivity(intent);
        }

        if (remindBean.getMsgType() == 5) {
            Intent intent = new Intent(this, FallPositionActivity.class);
//            intent.putExtra("member", familyMemberList.get(currentPos));
            intent.putExtra("remind", remindBean);
            intent.putExtra("type", 1);
            startActivity(intent);
        }

        if (remindBean.getMsgType() == 2) {
            Intent intent = new Intent(this, FallPositionActivity.class);
            intent.putExtra("remind", remindBean);
            intent.putExtra("type", 2);
            startActivity(intent);
        }

        mPresenter.msgRead(remindBean.getMsgType(),remindBean.getMsgId());
    }

    @Override
    public void msgReadSuccess() {

    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        mPresenter.msgList(pageIndex, accountId);
    }

    @Override
    public void onLoadMoreRequested() {
        pageIndex++;
        mPresenter.msgList(pageIndex, accountId);
    }
}
