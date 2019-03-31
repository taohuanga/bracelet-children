package os.bracelets.children.app.edit;

import java.util.List;

import os.bracelets.children.bean.LabelBean;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

public interface LabelContract {


    interface View extends BaseView<Presenter>{

       void loadTagSuccess(List<LabelBean> list);

       void setTagSuccess();

    }


    abstract class Presenter extends BasePresenter<View>{

        public Presenter(View mView) {
            super(mView);
        }


        abstract void getTagList();

        abstract void setTag(String accountId,String labelIds);
    }
}
