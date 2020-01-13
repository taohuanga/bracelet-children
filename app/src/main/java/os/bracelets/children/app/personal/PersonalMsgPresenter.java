package os.bracelets.children.app.personal;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.SPUtils;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.MyApplication;
import os.bracelets.children.bean.UserInfo;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.ImageBase64;

/**
 * Created by lishiyou on 2019/2/23.
 */

public class PersonalMsgPresenter extends PersonalMsgContract.Presenter {

    public PersonalMsgPresenter(PersonalMsgContract.View mView) {
        super(mView);
    }

    @Override
    void userInfo() {
        ApiRequest.userInfo(new HttpSubscriber() {

            @Override
            public void onStart() {
                super.onStart();
                if (mView != null) {
                    mView.showLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (mView != null) {
                    mView.hideLoading();
                }
                if (result.code.equals(AppConfig.SUCCESS)) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        UserInfo info = UserInfo.parseBean(object);
                        if (mView != null)
                            mView.loadInfoSuccess(info);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    void uploadImage(String imagePath) {
        if (TextUtils.isEmpty(imagePath))
            return;
        File file = new File(imagePath);
        if (!file.exists())
            return;
        String imageKey = ImageBase64.imageConvertBase64(imagePath);
        ApiRequest.uploadImage(1, imageKey, new HttpSubscriber() {

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
                        String serverPath = object.optString("data");
                        if (mView != null)
                            mView.uploadImageSuccess(serverPath);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showShort(result.errorMessage);
                }
            }
        });
    }

    @Override
    void modifyData(String portrait, String nickName, String realName, String birthday, int sex) {
        ApiRequest.modifyData(portrait, nickName, realName, birthday, sex, new HttpSubscriber() {
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
                        mView.updateMsgSuccess();
                }
            }
        });
    }
}
