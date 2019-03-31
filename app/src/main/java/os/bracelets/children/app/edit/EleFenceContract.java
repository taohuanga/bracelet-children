package os.bracelets.children.app.edit;

import java.util.List;

import os.bracelets.children.bean.EleFence;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface EleFenceContract {

    interface View extends BaseView<Presenter> {

        void loadEleFenceSuccess(List<EleFence> list);

    }


    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void eleFenceList(String accountId);
    }
}
