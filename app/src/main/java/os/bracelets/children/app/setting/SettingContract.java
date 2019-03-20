package os.bracelets.children.app.setting;

import os.bracelets.children.bean.BaseInfo;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/18.
 */

public interface SettingContract {

    interface View extends BaseView<Presenter> {

        void loadInfoSuccess(BaseInfo info);

    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void loadBaseInfo();
    }

}
