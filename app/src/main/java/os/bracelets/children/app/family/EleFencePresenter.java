package os.bracelets.children.app.family;

import aio.health2world.http.HttpResult;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

public class EleFencePresenter extends EleFenceContract.Presenter {

    public EleFencePresenter(EleFenceContract.View mView) {
        super(mView);
    }

    @Override
    void eleFenceList(String accountId) {
        ApiRequest.fenceList(accountId, new HttpSubscriber() {



            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);

            }
        });
    }
}
