package os.bracelets.children.app.home;

import java.util.List;

import os.bracelets.children.bean.DailySports;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.bean.WeatherInfo;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/3/24.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void loginWeatherSuccess(WeatherInfo info);

        void  loadMsgSuccess(List<RemindBean> list);

        void relativeSuccess(List<FamilyMember> list);

        void dailySportsSuccess(DailySports sports);

        void sportTrendSuccess(List<DailySports> list);

        void loadFamilySuccess(List<FamilyMember> list);

    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void getWeather();

        abstract void relative();

        abstract void familyList();

        abstract void msgList(int pageNo,String accountId);

        abstract void dailySports(String accountId);

        abstract void parentSportTrend(String accountId);
    }
}
