package os.bracelets.children.app.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.MVPBaseFragment;

/**
 * Created by lishiyou on 2019/3/20.
 */

public class HomeFragment extends MVPBaseFragment<HomeContract.Presenter> implements HomeContract.View {

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private List<FamilyMember> familyMemberList;
//    private CardFragmentPagerAdapter mFragmentCardAdapter;
//    private ShadowTransformer mFragmentCardShadowTransformer;

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
        mViewPager = findView(R.id.viewPager);
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

        mCardAdapter = new CardPagerAdapter();
//        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
//        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
//        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
//        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));
//        mFragmentCardAdapter = new CardFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
//                dpToPixels(2, getActivity()));

//        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);



        mPresenter.relative();
        mPresenter.msgList(String.valueOf(2));
    }

    @Override
    public void loadMsgSuccess() {

    }

    @Override
    public void relativeSuccess(List<FamilyMember> list) {
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
}
