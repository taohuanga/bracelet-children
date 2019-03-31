package os.bracelets.children.app.edit;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.EleFence;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

public class EleFencePresenter extends EleFenceListContract.Presenter {

    public EleFencePresenter(EleFenceListContract.View mView) {
        super(mView);
    }

    @Override
    void eleFenceList(final String accountId) {
        ApiRequest.fenceList(accountId, new HttpSubscriber() {

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
                        JSONArray array = new JSONArray(new Gson().toJson(result.data));
                        List<EleFence> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            EleFence fence = EleFence.parseBean(object);
                            list.add(fence);
                        }
                        if (mView != null)
                            mView.loadEleFenceSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
