package os.bracelets.children.app.family;

import java.util.List;

import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface MsgListContract {

    interface View extends BaseView<Presenter> {

        void loadMsgSuccess(List<RemindBean> list);

        void  msgReadSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void msgList(int pageNo, String accountId);

        abstract void msgRead(int type,int msgId);
    }
}
