package os.bracelets.children.app.family;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.MVPBaseFragment;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class FamilyFragment extends MVPBaseFragment<FamilyContract.Presenter> implements FamilyContract.View {

    private RecyclerView recyclerView;

    private List<FamilyMember> familyMemberList;

    private FamilyAdapter familyAdapter;

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
        recyclerView = findView(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        familyMemberList = new ArrayList<>();
        familyAdapter = new FamilyAdapter(familyMemberList);
        recyclerView.setAdapter(familyAdapter);
        familyAdapter.bindToRecyclerView(recyclerView);
        familyAdapter.setEmptyView(R.layout.layout_empty_view);
        mPresenter.familyList();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void loadFamilySuccess(List<FamilyMember> list) {
        familyMemberList.clear();
        familyMemberList.addAll(list);
        familyAdapter.notifyDataSetChanged();
    }
}
