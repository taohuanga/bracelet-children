package os.bracelets.children.app.account;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.SPUtils;
import os.bracelets.children.AppConfig;
import os.bracelets.children.MyApplication;
import os.bracelets.children.bean.BaseInfo;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

/**
 * Created by lishiyou on 2019/1/24.
 */

public class LoginPresenter extends LoginContract.Presenter {


    public LoginPresenter(LoginContract.View mView) {
        super(mView);
    }

    @Override
    void login(String name, String password) {
        ApiRequest.login(name, password, new HttpSubscriber() {

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
                        SPUtils.put(MyApplication.getInstance(), AppConfig.TOKEN_ID, info.getTokenId());
                        SPUtils.put(MyApplication.getInstance(), AppConfig.USER_ID, info.getUserId() + "");
                        SPUtils.put(MyApplication.getInstance(), AppConfig.USER_IMG, info.getPortrait() + "");
                        SPUtils.put(MyApplication.getInstance(), AppConfig.USER_NICK, info.getNickName() + "");
                        mView.loginSuccess(info);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    void securityCode(int type, String phone) {
        ApiRequest.code(type, phone, new HttpSubscriber() {

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
                        mView.securityCodeSuccess();
                }
            }
        });
    }

    @Override
    void fastLogin(String phone, String securityCode) {
        ApiRequest.fastLogin(phone, securityCode, new HttpSubscriber() {

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
                        SPUtils.put(MyApplication.getInstance(), AppConfig.TOKEN_ID, info.getTokenId());
                        SPUtils.put(MyApplication.getInstance(), AppConfig.USER_ID, String.valueOf(info.getUserId()));
                        SPUtils.put(MyApplication.getInstance(), AppConfig.USER_IMG, info.getPortrait());
                        SPUtils.put(MyApplication.getInstance(), AppConfig.USER_NICK, info.getNickName());
                        SPUtils.put(MyApplication.getInstance(), AppConfig.USER_PHONE, info.getPhone());
                        mView.loginSuccess(info);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
