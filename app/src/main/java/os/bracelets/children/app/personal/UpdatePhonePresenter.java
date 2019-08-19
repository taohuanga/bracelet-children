package os.bracelets.children.app.personal;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.MD5Util;
import os.bracelets.children.AppConfig;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

/**
 * Created by lishiyou on 2019/2/20.
 */

public class UpdatePhonePresenter extends UpdatePhoneContract.Presenter {

    public UpdatePhonePresenter(UpdatePhoneContract.View mView) {
        super(mView);
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
    void updatePhone(String oldPhone, String newPhone, String code, String pwd) {
        ApiRequest.updatePhone(oldPhone, newPhone, MD5Util.getMD5String(pwd), code, new HttpSubscriber() {
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
                        mView.updatePhoneSuccess();
                }
            }
        });
    }
}
