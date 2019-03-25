package os.bracelets.children.app.home;

import java.util.List;

import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/3/24.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void  loadMsgSuccess();

        void relativeSuccess(List<FamilyMember> list);

    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void relative();

        abstract void msgList(String accountId);
    }
}
