package os.bracelets.children.app.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.recyclerview.CoverFlowLayoutManger;
import aio.health2world.recyclerview.DividerItemDecoration;
import aio.health2world.recyclerview.RecyclerCoverFlow;
import aio.health2world.scrollablelayout.ScrollableLayout;
import aio.health2world.utils.DateUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.app.contact.ContactActivity;
import os.bracelets.children.app.edit.DeviceBindActivity;
import os.bracelets.children.app.edit.EleFenceListActivity;
import os.bracelets.children.app.edit.LabelEditActivity;
import os.bracelets.children.app.family.DetailActivity;
import os.bracelets.children.app.family.FamilyAddActivity;
import os.bracelets.children.app.family.MsgListActivity;
import os.bracelets.children.app.family.MsgListPresenter;
import os.bracelets.children.app.family.RemindListActivity;
import os.bracelets.children.bean.DailySports;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.bean.WeatherInfo;
import os.bracelets.children.common.MVPBaseFragment;
import os.bracelets.children.common.MsgEvent;
import os.bracelets.children.utils.DataString;

import static os.bracelets.children.app.family.FamilyListFragment.REQUEST_FAMILY_CHANGED;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class HomeFragment1 extends MVPBaseFragment<HomeContract.Presenter> implements HomeContract.View,
        HomeTopAdapter.onItemClick {

    private RecyclerCoverFlow recyclerCoverFlow;

    private HomeTopAdapter topAdapter;

    private List<FamilyMember> familyMemberList;

    private LineChart lineChart;

    private TextView tvTime, tvWeather, tvStepNum, tvMore;

    private ImageView ivSports;

    private int currentPos;

    private LinearLayout llMsg, llSetTag, llBindDevice, llRemindList, llFence, llContact;

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home1;
    }

    @Override
    protected void initView() {
        recyclerCoverFlow = findView(R.id.recyclerCoverFlow);

        tvTime = findView(R.id.tvTime);
        tvMore = findView(R.id.tvMore);
        tvWeather = findView(R.id.tvWeather);
        tvStepNum = findView(R.id.tvStepNum);
        ivSports = findView(R.id.ivSports);

        llMsg = findView(R.id.llMsg);
        llSetTag = findView(R.id.llSetTag);
        llBindDevice = findView(R.id.llBindDevice);
        llRemindList = findView(R.id.llRemindList);
        llFence = findView(R.id.llFence);
        llContact = findView(R.id.llContact);

        lineChart = findView(R.id.lineChart);
        lineChart.setNoDataText("图表暂无数据");
        //设置是否可以缩放 x和y，默认true
        lineChart.setScaleXEnabled(true);
        lineChart.setScaleYEnabled(false);
    }

    @Override
    protected void initListener() {
        EventBus.getDefault().register(this);
        llMsg.setOnClickListener(this);
        llSetTag.setOnClickListener(this);
        llBindDevice.setOnClickListener(this);
        llRemindList.setOnClickListener(this);
        llContact.setOnClickListener(this);
        llFence.setOnClickListener(this);
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                currentPos = position;
                if (position == 0) {
//                    startActivity(new Intent(getActivity(), FamilyAddActivity.class));
                } else {
                    loadData(familyMemberList.get(position));
                }
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FamilyMember member = familyMemberList.get(currentPos);
                    Intent intent = new Intent(getActivity(), SportsListActivity.class);
                    intent.putExtra("member", member);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void initData() {
        tvTime.setText(DateUtil.getDate(new Date(System.currentTimeMillis())) + " " + DataString.getWeek());

        familyMemberList = new ArrayList<>();
        topAdapter = new HomeTopAdapter(getActivity(), this, familyMemberList);
        recyclerCoverFlow.setAdapter(topAdapter);

        mPresenter.getWeather();
        mPresenter.familyList();
    }

    @Override
    public void clickItem(int pos) {
        currentPos = pos;
        if (pos == 0) {
            startActivity(new Intent(getActivity(), FamilyAddActivity.class));
        } else {
            recyclerCoverFlow.smoothScrollToPosition(pos);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("accountId", String.valueOf(familyMemberList.get(pos).getAccountId()));
            intent.putExtra("relationShipId", String.valueOf(familyMemberList.get(pos).getRelationshipId()));
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (familyMemberList.size() == 1)
            return;
        int pos = currentPos == 0 ? 1 : currentPos;
        switch (v.getId()) {
            case R.id.llMsg:
                Intent intentMsg = new Intent(getActivity(), MsgListActivity.class);
                intentMsg.putExtra("accountId", String.valueOf(familyMemberList.get(pos).getAccountId()));
                startActivity(intentMsg);
                break;
            case R.id.llSetTag:
                Intent intentTag = new Intent(getActivity(), LabelEditActivity.class);
                intentTag.putExtra("member", familyMemberList.get(pos));
                startActivity(intentTag);
                break;
            case R.id.llBindDevice:
                Intent bindIntent = new Intent(getActivity(), DeviceBindActivity.class);
                bindIntent.putExtra("member", familyMemberList.get(pos));
                startActivity(bindIntent);
                break;
            case R.id.llRemindList:
                Intent remindIntent = new Intent(getActivity(), RemindListActivity.class);
                remindIntent.putExtra("member", familyMemberList.get(pos));
                startActivity(remindIntent);
                break;
            case R.id.llFence:
                Intent eleListIntent = new Intent(getActivity(), EleFenceListActivity.class);
                eleListIntent.putExtra("member", familyMemberList.get(pos));
                startActivity(eleListIntent);
                break;
            case R.id.llContact:
                Intent contactIntent = new Intent(getActivity(), ContactActivity.class);
                contactIntent.putExtra("member", familyMemberList.get(pos));
                startActivity(contactIntent);
                break;
        }
    }

    private void loadData(FamilyMember member) {
//        mPresenter.msgList(pageNo, String.valueOf(member.getAccountId()));
        mPresenter.dailySports(String.valueOf(member.getAccountId()));
        mPresenter.parentSportTrend(String.valueOf(member.getAccountId()));
    }

    @Override
    public void loadMsgSuccess(List<RemindBean> list) {
//        if (pageNo == 1) {
//            remindList.clear();
//        }
//        if (list.size() >= AppConfig.PAGE_SIZE) {
//            remindAdapter.loadMoreComplete();
//        } else {
//            remindAdapter.loadMoreEnd();
//        }
//        remindList.addAll(list);
//        remindAdapter.notifyDataSetChanged();
//        recyclerView.scrollToPosition(0);
    }

//    @Override
//    public void onLoadMoreRequested() {
//        pageNo++;
//        FamilyMember member = familyMemberList.get(currentPos);
//        mPresenter.msgList(pageNo, String.valueOf(member.getAccountId()));
//    }

    @Override
    public void loginWeatherSuccess(WeatherInfo info) {
        tvWeather.setText(info.getCity() + " " + info.getWeather());
    }

    @Override
    public void loadFamilySuccess(List<FamilyMember> list) {
        familyMemberList.clear();
        familyMemberList.add(new FamilyMember());
        if (list.size() > 0)
            familyMemberList.addAll(list);
        if (familyMemberList.size() == 1) {
            currentPos = 0;
        } else {
            currentPos = 1;
            loadData(familyMemberList.get(currentPos));
        }
        topAdapter.notifyDataSetChanged();
//        recyclerCoverFlow.smoothScrollToPosition(0);
    }

    @Override
    public void relativeSuccess(List<FamilyMember> list) {
//        if (list.size() == 0)
//            return;
//        currentPos = 0;
//        pageNo = 1;
//        familyMemberList.clear();
//        familyMemberList.addAll(list);
//        topAdapter.notifyDataSetChanged();
//        loadData(list.get(0));
    }

    @Override
    public void dailySportsSuccess(DailySports sports) {
        tvStepNum.setText(String.valueOf(sports.getStepNum()));
        int pos = currentPos == 0 ? 1 : currentPos;
        FamilyMember member = familyMemberList.get(pos);
        if (member.getSex() == 1)
            ivSports.setImageResource(R.drawable.icon_parent_man);
        else
            ivSports.setImageResource(R.drawable.icon_parent_woman);
    }


    @Override
    public void sportTrendSuccess(List<DailySports> list) {
        lineChart.removeAllViews();
        if (list.size() == 0)
            return;
        ArrayList<String> xValues = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DailySports sports = list.get(i);
            xValues.add(sports.getDailyTime());
            yValues.add(new Entry(i, sports.getStepNum()));
        }
        ChartManager.initLineChart(getActivity(), lineChart, xValues, yValues);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        if (event.getAction() == AppConfig.MSG_STEP_COUNT) {
            tvStepNum.setText(String.valueOf(event.getT()));
        }

        if (event.getAction() == REQUEST_FAMILY_CHANGED) {
            mPresenter.familyList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
