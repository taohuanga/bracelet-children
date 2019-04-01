package os.bracelets.children.app.home;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aio.health2world.http.CommonService;
import aio.health2world.http.HttpResult;
import aio.health2world.utils.Logger;
import aio.health2world.utils.SPUtils;
import okhttp3.ResponseBody;
import os.bracelets.children.AppConfig;
import os.bracelets.children.MyApplication;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.bean.WeatherInfo;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                        List<FamilyMember> list = new ArrayList<>();
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
                if(result.code.equals(AppConfig.SUCCESS)){
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        JSONArray array = object.optJSONArray("list");
                        List<RemindBean> list = new ArrayList<>();
                        for (int i = 0; i <array.length() ; i++) {
                            JSONObject obj = array.optJSONObject(i);
                            RemindBean remindBean = RemindBean.parseBean(obj);
                            list.add(remindBean);
                        }
                        if(mView!=null)
                            mView.loadMsgSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    void getWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restapi.amap.com/")
                .build();
        Map<String, String> map = new HashMap<>();
        map.put("key", "de0e5942b4033a3d6cfa3a0d31a0c756");
        map.put("city", (String) SPUtils.get(MyApplication.getInstance(), AppConfig.CITY_CODE, ""));
        map.put("extensions", "base");
        map.put("output", "JSON");

        retrofit
                .create(CommonService.class)
                .getWeather(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String data = response.body().string();
                            JSONObject obj = new JSONObject(data);
                            String result = obj.optString("info");
                            if (result.equals("OK")) {
                                JSONArray array = obj.optJSONArray("lives");
                                JSONObject object = array.getJSONObject(0);
                                WeatherInfo info = WeatherInfo.parseBean(object);
                                if (mView != null) {
                                    mView.loginWeatherSuccess(info);
                                }
                            }
                            Logger.i("lsy", data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                    }
                });
    }
}
