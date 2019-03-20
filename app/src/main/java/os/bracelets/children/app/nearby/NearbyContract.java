package os.bracelets.children.app.nearby;

import java.util.List;

import os.bracelets.children.bean.NearbyPerson;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/24.
 */

public interface NearbyContract {

    interface View extends BaseView<Presenter>{

        void loadPersonSuccess(List<NearbyPerson> list);


        void loadPersonError();


    }

    abstract class Presenter extends BasePresenter<View>{

        public Presenter(View mView) {
            super(mView);
        }

        abstract void nearbyList(int pageNo);
    }

}
