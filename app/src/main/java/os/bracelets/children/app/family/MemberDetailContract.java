package os.bracelets.children.app.family;

import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface MemberDetailContract {

    interface View extends BaseView<Presenter>{

        void  loadMemberInfoSuccess(FamilyMember member);

    }


    abstract class Presenter extends BasePresenter<View>{

        public Presenter(View mView) {
            super(mView);
        }


        abstract void memberInfo(String accountId);
    }

}
