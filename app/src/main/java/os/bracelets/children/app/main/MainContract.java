package os.bracelets.children.app.main;

import java.util.List;

import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.bean.WeatherInfo;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/1/27.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        //数据保存
        void loginWeatherSuccess(WeatherInfo info);

        void loadMsgSuccess(int stepNum,List<RemindBean> list);

//        void loadRemindSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void homeMsg();

//        abstract void remindList();

        abstract void getWeather();

    }

}
