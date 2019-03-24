package os.bracelets.children.app.news;

import os.bracelets.children.bean.InfoDetail;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/3/21.
 */

public interface InfoDetailContract {

    interface View extends BaseView<Presenter> {
        void  loadSuccess(InfoDetail detail);
    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View mView) {
            super(mView);
        }

        abstract void loadInfoDetail(String infoId);
    }

}
