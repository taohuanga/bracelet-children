package os.bracelets.children.app.main;

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
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.bean.WeatherInfo;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lishiyou on 2019/1/27.
 */

public class MainPresenter extends MainContract.Presenter {


    public MainPresenter(MainContract.View mView) {
        super(mView);
    }

}
