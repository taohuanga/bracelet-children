package os.bracelets.children.app.family;

import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface DetailContract {

    interface View extends BaseView<Presenter>{

        void  loadMemberInfoSuccess(FamilyMember member);

        void uploadImageSuccess(String serverImagePath);

        void updateMsgSuccess();

        void deleteSuccess();
    }


    abstract class Presenter extends BasePresenter<View>{

        public Presenter(View mView) {
            super(mView);
        }

        abstract void memberInfo(String accountId);

        abstract void uploadImage(String imagePath);

        abstract void updateMsg(String accountId, String profile, String nickName, String realName,
                                      int sex, String birthday, String height, String weight, String relationship,
                                      String phone);

        abstract void delFamilyMember(String ids);
    }

}
