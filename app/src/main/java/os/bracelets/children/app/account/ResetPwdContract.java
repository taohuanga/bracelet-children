package os.bracelets.children.app.account;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/21.
 */

public interface ResetPwdContract {

    interface View extends BaseView<Presenter> {
        void codeSuccess();

        void resetPwdSuccess();
    }


    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View mView) {
            super(mView);
        }

        abstract void code(int type, String phone);

        abstract void resetPwd(String phone,String pwd,String securityCode);
    }

}
