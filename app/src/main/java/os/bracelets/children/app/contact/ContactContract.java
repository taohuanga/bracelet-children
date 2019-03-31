package os.bracelets.children.app.contact;

import java.util.List;

import os.bracelets.children.bean.ContactBean;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/24.
 */

public interface ContactContract {

    interface View extends BaseView<Presenter> {

        void loadContactSuccess(List<ContactBean> contactList);

        void loadContactError();
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }


        abstract void contactList(int pageNo,String accountId);

    }

}
