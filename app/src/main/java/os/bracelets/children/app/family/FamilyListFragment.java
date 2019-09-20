package os.bracelets.children.app.family;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.utils.Logger;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.app.edit.EditNavActivity;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseFragment;
import os.bracelets.children.common.MsgEvent;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class FamilyListFragment extends MVPBaseFragment<FamilyContract.Presenter> implements FamilyContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemLongClickListener {

    public static final int REQUEST_FAMILY_CHANGED = 0x01;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private List<FamilyMember> familyMemberList;

    private FamilyListAdapter familyAdapter;

    private ImageView ivAdd;

    private int delPosition = 0;

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
        EventBus.getDefault().register(this);
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
        familyAdapter.setOnItemLongClickListener(this);
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
    public void loadFamilyError() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FamilyMember member = (FamilyMember) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("accountId", String.valueOf(member.getAccountId()));
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        delPosition = position;
        final FamilyMember member = (FamilyMember) adapter.getItem(position);
        new AlertDialog.Builder(getActivity())
                .setMessage("是否需要删除该亲人？")
                .setNegativeButton(getString(R.string.pickerview_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.sure1), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delFamilyMember(String.valueOf(member.getRelationshipId()));
                    }
                })
                .create()
                .show();
        return false;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.llEdit) {
            FamilyMember member = (FamilyMember) adapter.getItem(position);
            Intent intent = new Intent(getActivity(), EditNavActivity.class);
            intent.putExtra("member", member);
            startActivity(intent);
        }
    }

    @Override
    public void deleteSuccess() {
        familyMemberList.remove(delPosition);
        familyAdapter.notifyItemRemoved(delPosition);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ivAdd:
                Intent intent = new Intent(getActivity(), FamilyAddActivity.class);
                startActivityForResult(intent, REQUEST_FAMILY_CHANGED);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        if (event.getAction() == REQUEST_FAMILY_CHANGED) {
            onRefresh();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
//        if (requestCode == REQUEST_FAMILY_ADD) {
//            onRefresh();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
