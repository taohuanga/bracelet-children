package os.bracelets.children.app.navigate;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/24.
 */

public interface NavigateContract {

    interface View extends BaseView<Presenter> {

    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }
    }
}
