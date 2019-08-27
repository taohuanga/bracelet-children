package os.bracelets.children.app.contact;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.ContactBean;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

/**
 * Created by lishiyou on 2019/2/24.
 */

public class ContactPresenter extends ContactContract.Presenter {

    public ContactPresenter(ContactContract.View mView) {
        super(mView);
    }

    @Override
    void contactList(int pageNo, String accountId) {
        ApiRequest.contactList(accountId, pageNo, new HttpSubscriber() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null)
                    mView.loadContactError();
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (result.code.equals(AppConfig.SUCCESS)) {
                    try {
                        JSONArray array = new JSONArray(new Gson().toJson(result.data));
                        if (array != null) {
                            List<ContactBean> list = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
                                ContactBean contact = ContactBean.parseBean(object);
                                list.add(contact);
                            }
                            if (mView != null)
                                mView.loadContactSuccess(list);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (mView != null)
                        mView.loadContactError();
                }
            }
        });
    }

    @Override
    void contactDelete(String ids) {
        ApiRequest.delContacts(ids, new HttpSubscriber() {

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
                        mView.deleteSuccess();
                }
            }
        });
    }
}
