package os.bracelets.children.app.family;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.ImageBase64;

/**
 * Created by lishiyou on 2019/3/26.
 */

public class FamilyAddPresenter extends FamilyAddContract.Presenter {

    public FamilyAddPresenter(FamilyAddContract.View mView) {
        super(mView);
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
    void addFamilyMember(String accountId, String profile, String nickName, String realName,
                         int sex, String birthday, String height, String weight,
                         String relationship, String phone) {

        ApiRequest.familyAdd(accountId, profile, nickName, realName, sex, birthday, height, weight,
                relationship, phone, new HttpSubscriber() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        if(mView!=null)
                            mView.showLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(mView!=null)
                            mView.hideLoading();
                    }

                    @Override
                    public void onNext(HttpResult result) {
                        super.onNext(result);
                        if(mView!=null)
                            mView.hideLoading();
                    }
                });

    }
}
