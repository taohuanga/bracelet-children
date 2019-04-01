package os.bracelets.children.app.home;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aio.health2world.recyclerview.CoverFlowLayoutManger;
import aio.health2world.recyclerview.RecyclerCoverFlow;
import aio.health2world.utils.DateUtil;
import aio.health2world.utils.SPUtils;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.bean.WeatherInfo;
import os.bracelets.children.common.MVPBaseFragment;
import os.bracelets.children.common.MsgEvent;
import os.bracelets.children.utils.DataString;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class HomeFragment extends MVPBaseFragment<HomeContract.Presenter> implements HomeContract.View, HomeTopAdapter.onItemClick {

    private RecyclerCoverFlow recyclerCoverFlow;

    private HomeTopAdapter topAdapter;

    private List<FamilyMember> familyMemberList;

    private RecyclerView recyclerView;

    private List<RemindBean> remindList;

    private RemindAdapter remindAdapter;

    private TextView tvTime, tvWeather, tvStepNum;

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
        recyclerCoverFlow = findView(R.id.recyclerCoverFlow);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        tvTime = findView(R.id.tvTime);
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
//        mPresenter.msgList(String.valueOf(2));
    }

    @Override
    public void clickItem(int pos) {
        recyclerCoverFlow.smoothScrollToPosition(pos);
    }

    @Override
    public void loadMsgSuccess(List<RemindBean> list) {
        remindList.clear();
        remindList.addAll(list);
        remindAdapter.notifyDataSetChanged();
    }

    @Override
    public void loginWeatherSuccess(WeatherInfo info) {
        tvWeather.setText(info.getCity() + " " + info.getWeather());
    }

    @Override
    public void relativeSuccess(List<FamilyMember> list) {
        if (list.size() == 0)
            return;
        familyMemberList.clear();
        familyMemberList.addAll(list);
        topAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                FamilyMember member = familyMemberList.get(position);
                mPresenter.msgList(String.valueOf(member.getAccountId()));
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        if (event.getAction() == AppConfig.MSG_STEP_COUNT) {
            tvStepNum.setText(String.valueOf(event.getT()));
        }
    }
}
