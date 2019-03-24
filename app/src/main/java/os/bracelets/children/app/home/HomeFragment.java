package os.bracelets.children.app.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import os.bracelets.children.R;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.MVPBaseFragment;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class HomeFragment extends MVPBaseFragment<HomeContract.Presenter> implements HomeContract.View {

    private RecyclerView recyclerView;

    private List<RemindBean> remindList;

    private RemindAdapter remindAdapter;

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
        remindList = new ArrayList<>();
        remindAdapter = new RemindAdapter(remindList);
        recyclerView.setAdapter(remindAdapter);
        remindAdapter.bindToRecyclerView(recyclerView);
        remindAdapter.setEmptyView(R.layout.layout_empty_view);

        mPresenter.msgList(String.valueOf(2));
    }

    @Override
    public void loadMsgSuccess() {

    }

    @Override
    protected void initListener() {

    }
}
