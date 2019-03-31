package os.bracelets.children.app.edit;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import os.bracelets.children.R;
import os.bracelets.children.bean.EleFence;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class EleFenceListActivity extends MVPBaseActivity<EleFenceListContract.Presenter> implements
        EleFenceListContract.View {

    private TitleBar titleBar;

    private Button btnSave;

    private FamilyMember member;

    private RecyclerView recyclerView;

    private List<EleFence> eleFenceList;

    private EleFenceListAdapter eleFenceAdapter;

    @Override
    protected EleFenceListContract.Presenter getPresenter() {
        return new EleFencePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag_list;
    }

    @Override
    protected void initView() {
        btnSave = findView(R.id.btnSave);
        btnSave.setVisibility(View.GONE);
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "电子围栏", titleBar);
        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {

        eleFenceList = new ArrayList<>();
        eleFenceAdapter = new EleFenceListAdapter(eleFenceList);
        recyclerView.setAdapter(eleFenceAdapter);
        eleFenceAdapter.bindToRecyclerView(recyclerView);
        eleFenceAdapter.setEmptyView(R.layout.layout_empty_view);

        member = (FamilyMember) getIntent().getSerializableExtra("member");
        mPresenter.eleFenceList(String.valueOf(member.getAccountId()));
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

    @Override
    public void loadEleFenceSuccess(List<EleFence> list) {
        eleFenceList.clear();
        eleFenceList.addAll(list);
        eleFenceAdapter.notifyDataSetChanged();
    }
}
