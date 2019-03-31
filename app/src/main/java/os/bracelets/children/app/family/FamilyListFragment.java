package os.bracelets.children.app.family;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.R;
import os.bracelets.children.app.edit.EditNavActivity;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseFragment;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class FamilyListFragment extends MVPBaseFragment<FamilyContract.Presenter> implements FamilyContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private List<FamilyMember> familyMemberList;

    private FamilyListAdapter familyAdapter;

    private ImageView ivAdd;

    @Override
    protected FamilyPresenter getPresenter() {
        return new FamilyPresenter(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_family;
    }

    @Override
    protected void initView() {
        ivAdd = findView(R.id.ivAdd);
        refreshLayout = findView(R.id.refreshLayout);
        recyclerView = findView(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        refreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.appThemeColor));
    }

    @Override
    protected void initData() {
        familyMemberList = new ArrayList<>();
        familyAdapter = new FamilyListAdapter(familyMemberList);
        recyclerView.setAdapter(familyAdapter);
        familyAdapter.bindToRecyclerView(recyclerView);
        familyAdapter.setEmptyView(R.layout.layout_empty_view);
        onRefresh();
    }

    @Override
    protected void initListener() {
        ivAdd.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        familyAdapter.setOnItemClickListener(this);
        familyAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        mPresenter.familyList();
    }

    @Override
    public void loadFamilySuccess(List<FamilyMember> list) {
        refreshLayout.setRefreshing(false);
        familyMemberList.clear();
        familyMemberList.addAll(list);
        familyAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FamilyMember member = (FamilyMember) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("accountId", String.valueOf(member.getAccountId()));
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.imgEdit) {
            FamilyMember member = (FamilyMember) adapter.getItem(position);
            Intent intent = new Intent(getActivity(), EditNavActivity.class);
            intent.putExtra("member", member);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ivAdd:
                startActivity(new Intent(getActivity(), FamilyAddActivity.class));
                break;
        }
    }


}
