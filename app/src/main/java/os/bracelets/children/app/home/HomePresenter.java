package os.bracelets.children.app.home;

import aio.health2world.http.HttpResult;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

/**
 * Created by lishiyou on 2019/3/24.
 */

public class HomePresenter extends HomeContract.Presenter{

    public HomePresenter(HomeContract.View mView) {
        super(mView);
    }

    @Override
    void msgList(String accountId) {
        ApiRequest.msgList(accountId, new HttpSubscriber() {
            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
            }
        });
    }
}
