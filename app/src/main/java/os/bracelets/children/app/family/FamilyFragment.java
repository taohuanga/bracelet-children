package os.bracelets.children.app.family;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
        ivAdd.setOnClickListener(this);
    }

    @Override
    public void loadFamilySuccess(List<FamilyMember> list) {
        familyMemberList.clear();
        familyMemberList.addAll(list);
        familyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ivAdd:
                startActivity(new Intent(getActivity(),FamilyAddActivity.class));
                break;
        }
    }
}
