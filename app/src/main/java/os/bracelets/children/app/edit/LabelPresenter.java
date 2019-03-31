package os.bracelets.children.app.edit;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.LabelBean;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

public class LabelPresenter extends LabelContract.Presenter {

    public LabelPresenter(LabelContract.View mView) {
        super(mView);
    }

    @Override
    void getTagList() {
        ApiRequest.tagList(new HttpSubscriber() {

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
                        List<LabelBean> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.optJSONObject(i);
                            LabelBean labelBean = LabelBean.parseBean(obj);
                            list.add(labelBean);
                        }
                        if (mView != null)
                            mView.loadTagSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    void setTag(String accountId, String labelIds) {
        ApiRequest.setTag(accountId, labelIds, new HttpSubscriber() {

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
                    if (mView != null)
                        mView.setTagSuccess();
                }
            }
        });
    }
}
