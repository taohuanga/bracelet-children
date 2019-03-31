package os.bracelets.children.app.edit;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface EleFenceAddContract {

    interface View extends BaseView<Presenter> {


    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }
    }

}
