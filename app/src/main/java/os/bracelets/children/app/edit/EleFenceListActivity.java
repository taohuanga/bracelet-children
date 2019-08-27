package os.bracelets.children.app.edit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import os.bracelets.children.R;
import os.bracelets.children.bean.EleFence;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class EleFenceListActivity extends MVPBaseActivity<EleFenceListContract.Presenter> implements
        EleFenceListContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    private TitleBar titleBar;

    private Button btnSave;

    private FamilyMember member;

    private RecyclerView recyclerView;

    private List<EleFence> eleFenceList;

    private EleFenceListAdapter eleFenceAdapter;

    private int delPosition = 0;

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
        eleFenceAdapter.setOnItemClickListener(this);
        eleFenceAdapter.setOnItemLongClickListener(this);
        titleBar.addAction(new TitleBar.TextAction("添加围栏") {
            @Override
            public void performAction(View view) {
                Intent eleAddIntent = new Intent(EleFenceListActivity.this, EleFenceAddActivity.class);
                eleAddIntent.putExtra("member", member);
                startActivityForResult(eleAddIntent, 0x01);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EleFence eleFence = (EleFence) adapter.getItem(position);
        Intent eleAddIntent = new Intent(EleFenceListActivity.this, EleFenceAddActivity.class);
        eleAddIntent.putExtra("member", member);
        eleAddIntent.putExtra("eleFence", eleFence);
        startActivityForResult(eleAddIntent, 0x01);

    }


    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        delPosition = position;
        final EleFence eleFence = (EleFence) adapter.getItem(position);
        new AlertDialog.Builder(this)
                .setMessage("是否需要删除该电子围栏？")
                .setNegativeButton(getString(R.string.pickerview_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.sure1), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delFence(String.valueOf(eleFence.getId()));
                    }
                })
                .create()
                .show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 0x01) {
            mPresenter.eleFenceList(String.valueOf(member.getAccountId()));
        }
    }

    @Override
    public void deleteSuccess() {
        eleFenceList.remove(delPosition);
        eleFenceAdapter.notifyItemRemoved(delPosition);
    }

    @Override
    public void loadEleFenceSuccess(List<EleFence> list) {
        eleFenceList.clear();
        eleFenceList.addAll(list);
        eleFenceAdapter.notifyDataSetChanged();
    }
}
