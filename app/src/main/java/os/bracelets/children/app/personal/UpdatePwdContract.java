package os.bracelets.children.app.personal;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/20.
 */

public interface UpdatePwdContract {

    interface View extends BaseView<Presenter> {

        void codeSuccess();

        void resetPwdSuccess();

    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View mView) {
            super(mView);
        }

        abstract void code(int type, String phone);

//        abstract void updatePwd(String oldPwd, String newPwd);

        abstract void resetPwd(String phone,String oldPwd,String password,String securityCode);
    }

}
