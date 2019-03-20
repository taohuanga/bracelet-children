package os.bracelets.children.app.main;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import os.bracelets.children.R;
import os.bracelets.children.app.family.FamilyFragment;
import os.bracelets.children.app.home.HomeFragment;
import os.bracelets.children.app.mine.MineFragment;
import os.bracelets.children.app.news.NewsFragment;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.view.HomeTabs;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends MVPBaseActivity<MainContract.Presenter> implements MainContract.View
        , HomeTabs.OnCheckedChangeListener {

    private HomeFragment homeFragment;

    private FamilyFragment familyFragment;

    private NewsFragment newsFragment;

    private MineFragment mineFragment;

    private FragmentManager fragmentManager;

    private HomeTabs homeTabs;

    @Override
    protected MainContract.Presenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        homeTabs = findView(R.id.homeTabs);
        fragmentManager = getSupportFragmentManager();
        onTabChecked(0);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        homeTabs.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onTabChecked(int position) {
        setTabSelection(position);
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标
     */
    private void setTabSelection(int index) {
        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况 这里使用hide而不是remove
        hideAllFragments(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    // 如果androidFragment为空，则创建一个并添加到界面上
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.container, homeFragment);
                } else
                    // 如果androidFragment不为空，则直接将它显示出来
                    transaction.show(homeFragment);
                break;
            case 1:
                if (familyFragment == null) {
                    familyFragment = new FamilyFragment();
                    transaction.add(R.id.container, familyFragment);
                } else
                    transaction.show(familyFragment);
                break;
            case 2:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.container, newsFragment);
                } else
                    transaction.show(newsFragment);
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.container, mineFragment);
                } else
                    transaction.show(mineFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideAllFragments(FragmentTransaction transaction) {
        if (homeFragment != null)
            transaction.hide(homeFragment);

        if (familyFragment != null)
            transaction.hide(familyFragment);

        if (newsFragment != null)
            transaction.hide(newsFragment);

        if (mineFragment != null)
            transaction.hide(mineFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (homeFragment != null)
            homeFragment = null;

        if (familyFragment != null)
            familyFragment = null;

        if (newsFragment != null)
            newsFragment = null;

        if (mineFragment != null)
            mineFragment = null;
    }
}
