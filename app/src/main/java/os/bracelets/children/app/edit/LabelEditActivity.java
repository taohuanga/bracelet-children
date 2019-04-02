package os.bracelets.children.app.edit;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.LabelBean;
import os.bracelets.children.bean.LabelSection;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class LabelEditActivity extends MVPBaseActivity<LabelContract.Presenter> implements LabelContract.View,
        BaseQuickAdapter.OnItemClickListener {

    private TitleBar titleBar;

    private FamilyMember member;

    private List<LabelSection> labelList;

    private LabelListAdapter listAdapter;

    private RecyclerView recyclerView;

    private Button btnSave;

    @Override
    protected LabelContract.Presenter getPresenter() {
        return new LabelPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag_list;
    }

    @Override
    protected void initView() {
        btnSave = findView(R.id.btnSave);
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "设置标签", titleBar);
        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");
        labelList = new ArrayList<>();
        listAdapter = new LabelListAdapter(labelList);
        recyclerView.setAdapter(listAdapter);
        listAdapter.bindToRecyclerView(recyclerView);
        listAdapter.setEmptyView(R.layout.layout_empty_view);

        mPresenter.getTagList();
    }

    @Override
    protected void initListener() {
        btnSave.setOnClickListener(this);
        listAdapter.setOnItemClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LabelSection labelSection = (LabelSection) adapter.getItem(position);
        if (labelSection.isHeader)
            return;
        if (labelSection.t.isChecked())
            labelSection.t.setChecked(false);
        else
            labelSection.t.setChecked(true);
        listAdapter.notifyItemChanged(position);
    }

    @Override
    public void loadTagSuccess(List<LabelBean> list) {
        labelList.clear();
        int labelType = 0;
        for (LabelBean label : list) {
            if (labelType != label.getLabelType()) {
                LabelSection section = new LabelSection(true, label.getLabelTypeDesc());
                labelList.add(section);
                labelType = label.getLabelType();
            }
            labelList.add(new LabelSection(label));
        }
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void setTagSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnSave:
                saveTag();
                break;
        }
    }

    private void saveTag() {
        if (member == null || member.getAccountId() == 0) {
            ToastUtil.showShort("亲人数据异常");
            return;
        }
        //是否有选择中标签
        String labelIds = "";
        for (LabelSection label : labelList) {
            if (label.isHeader)
                break;
            if (label.t.isChecked())
                labelIds += label.t.getLabelId() + ";";
        }
        if (TextUtils.isEmpty(labelIds)) {
            ToastUtil.showShort("请选择标签");
            return;
        }
        labelIds = labelIds.substring(0, labelIds.length() - 1);

        mPresenter.setTag(String.valueOf(member.getAccountId()), labelIds);
    }
}
