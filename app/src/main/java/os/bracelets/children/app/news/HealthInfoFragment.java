package os.bracelets.children.app.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.HealthInfo;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.MVPBaseFragment;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class HealthInfoFragment extends MVPBaseFragment<HealthInfoContract.Presenter> implements HealthInfoContract.View,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private HealthInfoAdapter infoAdapter;

    private List<HealthInfo> infoList;

    private int pageNo = 1;

    private int infoType = 0;

    @Override
    protected HealthInfoContract.Presenter getPresenter() {
        return new HealthInfoPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findView(R.id.view).setVisibility(View.VISIBLE);
        }
        recyclerView = findView(R.id.recyclerView);
        refreshLayout = findView(R.id.refreshLayout);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        refreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.appThemeColor));

        infoList = new ArrayList<>();
        infoAdapter = new HealthInfoAdapter(infoList);
        recyclerView.setAdapter(infoAdapter);
        infoAdapter.bindToRecyclerView(recyclerView);
        infoAdapter.setEmptyView(R.layout.layout_empty_view);

        onRefresh();
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
        infoAdapter.setOnItemClickListener(this);
        infoAdapter.setOnLoadMoreListener(this, recyclerView);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        refreshLayout.setRefreshing(true);
        mPresenter.informationList(infoType, pageNo, "");
    }

    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        mPresenter.informationList(infoType, pageNo, "");
    }

    @Override
    public void loadInfoSuccess(List<HealthInfo> list) {
        refreshLayout.setRefreshing(false);
        if (pageNo == 1)
            infoList.clear();
        infoList.addAll(list);
        infoAdapter.notifyDataSetChanged();

        if (list.size() >= AppConfig.PAGE_SIZE)
            infoAdapter.loadMoreComplete();
        else
            infoAdapter.loadMoreEnd();
    }

    @Override
    public void loadInfoError() {
        refreshLayout.setRefreshing(false);
        if (pageNo > 1)
            pageNo--;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HealthInfo info = (HealthInfo) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
        intent.putExtra(InfoDetailActivity.INFO_ID, info.getInformationId());
        startActivity(intent);
    }
}
