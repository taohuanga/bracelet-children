package os.bracelets.children.app.family;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

/**
 * Created by lishiyou on 2019/3/24.
 */

public class FamilyPresenter extends FamilyContract.Presenter {

    public FamilyPresenter(FamilyContract.View mView) {
        super(mView);
    }

    @Override
    void familyList() {
        ApiRequest.familyList(new HttpSubscriber() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null)
                    mView.loadFamilyError();
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (result.code.equals(AppConfig.SUCCESS)) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        JSONArray array = object.optJSONArray("list");
                        List<FamilyMember> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.optJSONObject(i);
                            FamilyMember member = FamilyMember.parseBean(obj);
                            list.add(member);
                        }
                        if (mView != null)
                            mView.loadFamilySuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (mView != null)
                        mView.loadFamilyError();
                }
            }
        });
    }

    @Override
    void delFamilyMember(String ids) {
        ApiRequest.relationshipDelete(ids, new HttpSubscriber() {
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
                if(result.code.equals(AppConfig.SUCCESS)){
                    mView.deleteSuccess();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                if(mView!=null)
                    mView.showLoading();
            }
        });
    }
}
