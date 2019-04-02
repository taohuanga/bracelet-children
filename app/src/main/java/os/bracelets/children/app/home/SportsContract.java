package os.bracelets.children.app.home;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface SportsContract {

    interface View extends BaseView<Presenter> {

    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }
    }

}
