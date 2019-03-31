package os.bracelets.children.app.family;

import java.util.List;

import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/3/24.
 */

public interface FamilyContract {

    interface View extends BaseView<Presenter> {

        void loadFamilySuccess(List<FamilyMember> list);

        void loadFamilyError();
    }


    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View mView) {
            super(mView);
        }

        abstract void familyList();
    }

}
