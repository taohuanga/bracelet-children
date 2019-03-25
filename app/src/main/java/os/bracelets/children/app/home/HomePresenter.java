package os.bracelets.children.app.home;

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

public class HomePresenter extends HomeContract.Presenter{

    public HomePresenter(HomeContract.View mView) {
        super(mView);
    }

    @Override
    void relative() {
        ApiRequest.relative(new HttpSubscriber() {
            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if(result.code.equals(AppConfig.SUCCESS)){
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        JSONArray array = object.optJSONArray("list");
                        List<FamilyMember> list = new ArrayList<FamilyMember>();
                        for (int i = 0; i <array.length() ; i++) {
                            JSONObject obj = array.optJSONObject(i);
                            FamilyMember member = FamilyMember.parseBean(obj);
                            list.add(member);
                        }
                        if(mView!=null)
                            mView.relativeSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    void msgList(String accountId) {
        ApiRequest.msgList(accountId, new HttpSubscriber() {
            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
            }
        });
    }
}
