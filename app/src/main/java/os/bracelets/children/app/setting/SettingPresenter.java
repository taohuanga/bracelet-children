package os.bracelets.children.app.setting;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.BaseInfo;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

/**
 * Created by lishiyou on 2019/2/18.
 */

public class SettingPresenter extends SettingContract.Presenter {

    public SettingPresenter(SettingContract.View mView) {
        super(mView);
    }

    @Override
    void loadBaseInfo() {
        ApiRequest.userInfo(new HttpSubscriber() {

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
                        BaseInfo info = BaseInfo.parseBean(object);
                        if (mView != null)
                            mView.loadInfoSuccess(info);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
