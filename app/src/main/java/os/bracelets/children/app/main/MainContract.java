package os.bracelets.children.app.main;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/1/27.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {


    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View mView) {
            super(mView);
        }

        abstract void uploadLocation();
    }

}
