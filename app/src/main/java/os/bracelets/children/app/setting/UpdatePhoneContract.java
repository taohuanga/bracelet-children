package os.bracelets.children.app.setting;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/20.
 */

public interface UpdatePhoneContract {

    interface View extends BaseView<Presenter> {

    }


    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }
    }
}
