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

public class HomeFragment extends MVPBaseFragment<HomeContract.Presenter> implements HomeContract.View {

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
//    private List<FamilyMember> familyMemberList;
//    private CardFragmentPagerAdapter mFragmentCardAdapter;
//    private ShadowTransformer mFragmentCardShadowTransformer;

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
        mViewPager = findView(R.id.viewPager);
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

        mCardAdapter = new CardPagerAdapter();

        tvTime.setText(DateUtil.getDate(new Date(System.currentTimeMillis())) + " " + DataString.getWeek());


        mPresenter.getWeather();
        mPresenter.relative();
        mPresenter.msgList(String.valueOf(2));
    }

    @Override
    public void loadMsgSuccess() {

    }

    @Override
    public void loginWeatherSuccess(WeatherInfo info) {
        tvWeather.setText(info.getCity() + " " + info.getWeather());
    }

    @Override
    public void relativeSuccess(List<FamilyMember> list) {
        if (list.size() == 0)
            return;
        for (FamilyMember member : list) {
            mCardAdapter.addCardItem(member);
        }
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);
    }

    @Override
    protected void initListener() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {
        if (event.getAction() == AppConfig.MSG_STEP_COUNT) {
            tvStepNum.setText(String.valueOf(event.getT()));
        }
    }
}
