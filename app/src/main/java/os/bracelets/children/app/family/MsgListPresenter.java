package os.bracelets.children.app.family;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

public class MsgListPresenter extends MsgListContract.Presenter {

    public MsgListPresenter(MsgListContract.View mView) {
        super(mView);
    }

    @Override
    void msgList(int pageNo, String accountId) {
        ApiRequest.msgList(pageNo, accountId, new HttpSubscriber() {

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
                        JSONArray array = object.optJSONArray("list");
                        List<RemindBean> list = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.optJSONObject(i);
                            RemindBean remindBean = RemindBean.parseBean(obj);
                            list.add(remindBean);
                        }
                        if (mView != null)
                            mView.loadMsgSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (mView != null)
                            mView.loadMsgSuccess(new ArrayList<RemindBean>());
                    }
                }
            }
        });
    }

    @Override
    void msgRead(int type, int msgId) {
        ApiRequest.msgRead(type, msgId, new HttpSubscriber() {
            @Override
            public void onNext(HttpResult result) {
                if (result.code.equals(AppConfig.SUCCESS)) {
                    if (mView != null)
                        mView.msgReadSuccess();
                }
            }
        });
    }
}
