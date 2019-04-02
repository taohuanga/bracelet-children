package os.bracelets.children.app.home;

import java.util.List;

import os.bracelets.children.bean.DailySports;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface SportsContract {

    interface View extends BaseView<Presenter> {

        void dailySportsSuccess(List<DailySports> sportsList);

    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void dailySports(String accountId);
    }

}
