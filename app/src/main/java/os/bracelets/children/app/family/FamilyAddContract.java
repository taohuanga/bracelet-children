package os.bracelets.children.app.family;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/3/26.
 */

public interface FamilyAddContract {

    interface View extends BaseView<Presenter> {

        void uploadImageSuccess(String imageUrl);


        void addMemberSuccess();
    }


    abstract class Presenter extends BasePresenter<View> {

        public Presenter(View mView) {
            super(mView);
        }

        abstract void uploadImage(String imagePath);


        abstract void addFamilyMember(String accountId, String profile, String nickName, String realName,
                                      int sex, String birthday, String height, String weight, String relationship,
                                      String phone);
    }

}
