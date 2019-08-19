package os.bracelets.children.app.personal;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/20.
 */

public interface UpdatePhoneContract {

    interface View extends BaseView<Presenter> {
        void securityCodeSuccess();

        void updatePhoneSuccess();
    }


    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }


        //获取手机验证码
        abstract void securityCode(int type, String phone);

        abstract void updatePhone(String oldPhone, String newPhone,String code,String pwd);
    }
}
