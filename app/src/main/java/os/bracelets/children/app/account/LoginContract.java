package os.bracelets.children.app.account;

import os.bracelets.children.bean.BaseInfo;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/1/24.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        //数据保存
        void loginSuccess(BaseInfo info);

        void securityCodeSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }


        //获取手机验证码
        abstract void securityCode(int type, String phone,String areaCode);

        abstract void login(String name, String password);


        abstract void fastLogin(String phone, String securityCode);


    }

}
