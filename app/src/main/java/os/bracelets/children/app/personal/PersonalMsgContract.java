package os.bracelets.children.app.personal;

import os.bracelets.children.bean.UserInfo;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/23.
 */

public interface PersonalMsgContract {

    interface View extends BaseView<Presenter> {
        void loadInfoSuccess(UserInfo info);

        void uploadImageSuccess(String imageUrl);

        void updateMsgSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void userInfo();

        abstract void uploadImage(String imagePath);

        abstract void saveBaseInfo(String profile, String nickName, String realName,String account, int sex);
    }

}
