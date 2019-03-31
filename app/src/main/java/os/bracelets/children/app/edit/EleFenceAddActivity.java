package os.bracelets.children.app.edit;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.SPUtils;
import aio.health2world.utils.ToastUtil;
import aio.health2world.view.LoadingDialog;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class EleFenceAddActivity extends MVPActivity<EleFenceAddContract.Presenter> implements
        EleFenceAddContract.View, GeocodeSearch.OnGeocodeSearchListener {

    private TitleBar titleBar;

    private MapView mapView;

    private AMap aMap;

    private Marker screenMarker = null;

    private GeocodeSearch geocodeSearch;

    private TextView tvAddress;

    private TextView btnAdd;

    private FamilyMember member;

    private LatLng latLng;

    private LoadingDialog dialog;

    @Override
    protected EleFenceAddContract.Presenter getPresenter() {
        return new EleFenceAddPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elefence_add);
        tvAddress = findViewById(R.id.tvAddress);
        btnAdd = findViewById(R.id.btnAdd);
        titleBar = findViewById(R.id.titleBar);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        geocodeSearch = new GeocodeSearch(this);

        initData();
    }


    private void initData() {
        dialog = new LoadingDialog(this);
        member = (FamilyMember) getIntent().getSerializableExtra("member");

        TitleBarUtil.setAttr(this, "", "添加电子围栏", titleBar);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        double latitude = Double.parseDouble((String) SPUtils.get(this, AppConfig.LATITUDE, ""));
        double longitude = Double.parseDouble((String) SPUtils.get(this, AppConfig.LONGITUDE, ""));
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(latitude, longitude), 18, 30, 30)));
        aMap.moveCamera(CameraUpdateFactory.zoomTo((float) 13.5));

        addListener();

    }

    private void addListener() {

        btnAdd.setOnClickListener(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkersToMap();
            }
        });

        // 设置可视范围变化时的回调的接口方法
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition position) {
                //屏幕中心的Marker跳动
                startJumpAnimation();
                latLng = position.target;
                LatLonPoint point = new LatLonPoint(latLng.latitude, latLng.longitude);
                RegeocodeQuery query = new RegeocodeQuery(point, 100,
                        GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                geocodeSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
            }
        });
    }

    private void addMarkersToMap() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        screenMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {
        if (screenMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = screenMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this, 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            screenMarker.setAnimation(animation);
            //开始动画
            screenMarker.startAnimation();

        } else {
            Log.e("amap", "screenMarker is null");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                addEleFence();
                break;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getFormatAddress();
                tvAddress.setText(addressName);
            }
        } else {
            ToastUtil.showShort(rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private void addEleFence() {
        if (latLng == null || TextUtils.isEmpty(tvAddress.getText().toString()))
            return;
        ApiRequest.fenceAdd(String.valueOf(member.getAccountId()), tvAddress.getText().toString(),
                String.valueOf(latLng.longitude), String.valueOf(latLng.latitude), "100",
                new HttpSubscriber() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        dialog.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(HttpResult result) {
                        super.onNext(result);
                        dialog.dismiss();
                        if (result.code.equals(AppConfig.SUCCESS)) {
                            ToastUtil.showShort("添加成功");
                            finish();
                        }
                    }
                });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
