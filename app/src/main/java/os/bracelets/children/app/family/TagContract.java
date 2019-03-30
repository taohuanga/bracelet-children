package os.bracelets.children.app.family;

import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface TagContract {


    interface View extends BaseView<Presenter>{

        void loadTagSuccess();

    }


    abstract class Presenter extends BasePresenter<View>{

        public Presenter(View mView) {
            super(mView);
        }

       abstract void getTagList();
    }
}
