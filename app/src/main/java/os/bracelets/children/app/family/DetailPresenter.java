package os.bracelets.children.app.family;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

public class DetailPresenter extends DetailContract.Presenter {

    public DetailPresenter(DetailContract.View mView) {
        super(mView);
    }


    @Override
    void memberInfo(String accountId) {
        ApiRequest.memberInfo(accountId, new HttpSubscriber() {

            @Override
            public void onStart() {
                super.onStart();
                if (mView != null)
                    mView.showLoading();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null)
                    mView.hideLoading();
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (mView != null)
                    mView.hideLoading();
                if (result.code.equals(AppConfig.SUCCESS)) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        FamilyMember member = FamilyMember.parseBean(object);
                        if (mView != null)
                            mView.loadMemberInfoSuccess(member);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
