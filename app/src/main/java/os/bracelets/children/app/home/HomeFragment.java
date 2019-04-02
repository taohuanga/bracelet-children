package os.bracelets.children.app.home;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.recyclerview.CoverFlowLayoutManger;
import aio.health2world.recyclerview.DividerItemDecoration;
import aio.health2world.recyclerview.RecyclerCoverFlow;
import aio.health2world.utils.DateUtil;
import aio.health2world.utils.SPUtils;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.DailySports;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.bean.WeatherInfo;
import os.bracelets.children.common.MVPBaseFragment;
import os.bracelets.children.common.MsgEvent;
import os.bracelets.children.utils.DataString;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class HomeFragment extends MVPBaseFragment<HomeContract.Presenter> implements HomeContract.View,
        HomeTopAdapter.onItemClick, BaseQuickAdapter.OnItemClickListener {

    private RecyclerCoverFlow recyclerCoverFlow;

    private HomeTopAdapter topAdapter;

    private List<FamilyMember> familyMemberList;

    private RecyclerView recyclerView;

    private List<RemindBean> remindList;

    private RemindAdapter remindAdapter;

    private TextView tvTime, tvWeather, tvStepNum, tvMore;

    private int currentPos;

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
        recyclerCoverFlow = findView(R.id.recyclerCoverFlow);

        recyclerView = findView(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));

        tvTime = findView(R.id.tvTime);
        tvMore = findView(R.id.tvMore);
        tvWeather = findView(R.id.tvWeather);
        tvStepNum = findView(R.id.tvStepNum);
    }

    @Override
    protected void initData() {
        remindList = new ArrayList<>();
        remindAdapter = new RemindAdapter(remindList);
        recyclerView.setAdapter(remindAdapter);
        remindAdapter.bindToRecyclerView(recyclerView);
        remindAdapter.setEmptyView(R.layout.layout_empty_view);

        tvTime.setText(DateUtil.getDate(new Date(System.currentTimeMillis())) + " " + DataString.getWeek());

        familyMemberList = new ArrayList<>();
        topAdapter = new HomeTopAdapter(getActivity(), this, familyMemberList);
        recyclerCoverFlow.setAdapter(topAdapter);

        mPresenter.getWeather();
        mPresenter.relative();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RemindBean remindBean = (RemindBean) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), FallPositionActivity.class);
        intent.putExtra("member", familyMemberList.get(currentPos));
        intent.putExtra("remind", remindBean);
        startActivity(intent);
    }

    @Override
    public void clickItem(int pos) {
        currentPos = pos;
        recyclerCoverFlow.smoothScrollToPosition(pos);
        loadData(familyMemberList.get(pos));
    }

    @Override
    public void loadMsgSuccess(List<RemindBean> list) {
        remindList.clear();
        remindList.addAll(list);
        remindAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void loginWeatherSuccess(WeatherInfo info) {
        tvWeather.setText(info.getCity() + " " + info.getWeather());
    }

    @Override
    public void relativeSuccess(List<FamilyMember> list) {
        if (list.size() == 0)
            return;
        currentPos = 0;
        familyMemberList.clear();
        familyMemberList.addAll(list);
        topAdapter.notifyDataSetChanged();
        loadData(list.get(0));
    }

    @Override
    public void dailySportsSuccess(DailySports sports) {
        tvStepNum.setText(String.valueOf(sports.getStepNum()));
    }

    @Override
    protected void initListener() {
        remindAdapter.setOnItemClickListener(this);
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                currentPos = position;
                loadData(familyMemberList.get(position));
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamilyMember member = familyMemberList.get(currentPos);
                Intent intent = new Intent(getActivity(), SportsListActivity.class);
                intent.putExtra("member", member);
                startActivity(intent);
            }
        });
    }


    private void loadData(FamilyMember member) {
        mPresenter.msgList(String.valueOf(member.getAccountId()));
        mPresenter.dailySports(String.valueOf(member.getAccountId()));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        if (event.getAction() == AppConfig.MSG_STEP_COUNT) {
            tvStepNum.setText(String.valueOf(event.getT()));
        }
    }
}
