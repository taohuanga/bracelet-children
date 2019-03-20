package os.bracelets.children.app.account;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/20.
 */

public interface RegisterContract {


    interface View extends BaseView<Presenter> {

        void codeSuccess();

        void registerSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View mView) {
            super(mView);
        }

        abstract void code(int type, String phone);

        abstract void register(String phone,String securityCode,String code,String password);
    }

}
