package os.bracelets.children.app.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapRouteActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import aio.health2world.glide_transformations.CropCircleTransformation;
import aio.health2world.http.HttpResult;
import aio.health2world.utils.SPUtils;
import os.bracelets.children.AppConfig;
import os.bracelets.children.MyApplication;
import os.bracelets.children.R;
import os.bracelets.children.app.about.AboutActivity;
import os.bracelets.children.app.about.FeedBackActivity;
import os.bracelets.children.app.account.LoginActivity;
import os.bracelets.children.app.nearby.NearbyActivity;
import os.bracelets.children.common.BaseFragment;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lishiyou on 2019/3/20.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class PersonalFragment extends BaseFragment implements INaviInfoCallback {

    private ImageView ivImage;

    private TextView tvName;

    private Button btnLogout;

    private View layoutUpdatePwd, layoutUpdateMsg, layoutNearby, layoutNav, layoutFeedBack, layoutAbout, layoutExit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findView(R.id.view).setVisibility(View.VISIBLE);
        }
        ivImage = findView(R.id.ivImage);
        tvName = findView(R.id.tvName);
        btnLogout = findView(R.id.btnLogout);
        layoutUpdatePwd = findView(R.id.layoutUpdatePwd);
        layoutUpdateMsg = findView(R.id.layoutUpdateMsg);
        layoutNearby = findView(R.id.layoutNearby);
        layoutFeedBack = findView(R.id.layoutFeedBack);
        layoutAbout = findView(R.id.layoutAbout);
        layoutNav = findView(R.id.layoutNav);
        layoutExit = findView(R.id.layoutExit);
        getUserInfo();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        setOnClickListener(btnLogout);
        setOnClickListener(layoutUpdatePwd);
        setOnClickListener(layoutUpdateMsg);
        setOnClickListener(layoutFeedBack);
        setOnClickListener(layoutAbout);
        setOnClickListener(layoutNearby);
        setOnClickListener(layoutNav);
        setOnClickListener(layoutExit);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnLogout:
            case R.id.layoutExit:
                logout();
                break;
            case R.id.layoutUpdatePwd:
                startActivity(new Intent(getActivity(), UpdatePwdActivity.class));
                break;
            case R.id.layoutUpdateMsg:
                startActivityForResult(new Intent(getActivity(), PersonalMsgActivity.class), 0x02);
                break;
            case R.id.layoutNearby:
                startActivity(new Intent(getActivity(), NearbyActivity.class));
                break;
            case R.id.layoutNav:
                AmapNaviParams params = new AmapNaviParams(new Poi("", null, ""),
                        null, new Poi("", null, ""), AmapNaviType.DRIVER);
                params.setUseInnerVoice(true);
                AmapNaviPage.getInstance().showRouteActivity(getActivity(), params,
                        this, AmapRouteActivity.class);
                break;
            case R.id.layoutFeedBack:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.layoutAbout:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }

    private void logout() {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage("确认退出登录吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SPUtils.put(MyApplication.getInstance(), AppConfig.IS_LOGIN, false);
//                        startActivity(new Intent(getActivity(), LoginActivity.class));
//                        getActivity().finish();
                        MyApplication.getInstance().logout(false);
                    }
                })
                .create()
                .show();
    }

    private void getUserInfo() {
        ApiRequest.userInfo(new HttpSubscriber() {
            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (result.code.equals(AppConfig.SUCCESS)) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
//                        "nickName":"李小琳",
//                                "portrait":"http://47.101.221.44/pic/0/group/logo/201903/1903300107188316.jpg",
                        String nickName = object.optString("nickName");
                        String portrait = object.optString("portrait");
                        tvName.setText(nickName);
                        Glide.with(MyApplication.getInstance())
                                .load(portrait)
                                .placeholder(R.mipmap.ic_default_portrait)
                                .error(R.mipmap.ic_default_portrait)
                                .bitmapTransform(new CropCircleTransformation(mContext))
                                .into(ivImage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 0x02) {
            getUserInfo();
        }
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
}
