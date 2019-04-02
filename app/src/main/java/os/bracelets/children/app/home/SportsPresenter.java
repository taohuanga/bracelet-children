package os.bracelets.children.app.home;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.http.HttpResult;
import os.bracelets.children.AppConfig;
import os.bracelets.children.bean.DailySports;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

public class SportsPresenter extends SportsContract.Presenter {

    public SportsPresenter(SportsContract.View mView) {
        super(mView);
    }

    @Override
    void dailySports(String accountId) {
        ApiRequest.dailySportsList(accountId, "", "", new HttpSubscriber() {

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
                    if (!TextUtils.isEmpty(result.data.toString())) {
                        try {
                            JSONArray array = new JSONArray(new Gson().toJson(result.data));
                            if (array != null) {
                                List<DailySports> list = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    DailySports sports = DailySports.parseBean(object);
                                    list.add(sports);
                                }
                                if (mView != null)
                                    mView.dailySportsSuccess(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
