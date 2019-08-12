package os.bracelets.children.app.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.bumptech.glide.Glide;

import aio.health2world.glide_transformations.CropCircleTransformation;
import aio.health2world.utils.AppUtils;
import aio.health2world.utils.DensityUtil;
import aio.health2world.utils.DeviceUtil;
import aio.health2world.utils.Logger;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class FallPositionActivity extends AppCompatActivity implements AMap.InfoWindowAdapter,
        View.OnClickListener, INaviInfoCallback {

    private TitleBar titleBar;

    private AMap aMap;

    private MapView mapView;

    private LatLng latLng;
    //0跌倒 1 预警 2电子围栏
    private int type = 0;

//    private GeocodeSearch geocodeSearch;

//    private FamilyMember member;

    private RemindBean remind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_position);

        titleBar = findViewById(R.id.titleBar);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        initData();

        addListener();
    }

    private void initData() {
//        member = (FamilyMember) getIntent().getSerializableExtra("member");
        remind = (RemindBean) getIntent().getSerializableExtra("remind");
        if (getIntent().hasExtra("type"))
            type = getIntent().getIntExtra("type", 0);

        if (type == 0) {
            TitleBarUtil.setAttr(this, "", "跌倒位置", titleBar);
        } else if (type == 2) {
            TitleBarUtil.setAttr(this, "", "电子围栏", titleBar);
        } else {
            TitleBarUtil.setAttr(this, "", "预警提醒", titleBar);
        }

        if (aMap == null)
            aMap = mapView.getMap();
//        geocodeSearch = new GeocodeSearch(this);
//        geocodeSearch.setOnGeocodeSearchListener(this);
        latLng = new LatLng(Double.parseDouble(remind.getLatitude()),
                Double.parseDouble(remind.getLongitude()));
        changeCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(latLng, 18, 30, 30)));
        aMap.clear();
        aMap.setInfoWindowAdapter(this);
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .infoWindowEnable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        marker.showInfoWindow();
    }

    private void addListener() {
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(R.layout.layout_custom_info_window, null);
        DisplayMetrics metric = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        Logger.i("lsy", "width=" + width + ",height=" + height);
//        ViewGroup.LayoutParams params = infoWindow.getLayoutParams();
//        params.width = width;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        infoWindow.setLayoutParams(params);
        render(marker, infoWindow);
        return infoWindow;
    }

//    @Override
//    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
//        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
//            if (result != null && result.getRegeocodeAddress() != null
//                    && result.getRegeocodeAddress().getFormatAddress() != null) {
//                address = result.getRegeocodeAddress().getFormatAddress();
//                if (tvAddress != null)
//                    tvAddress.setText(address);
//            }
//        }
//    }
//
//    @Override
//    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNav:
                AmapNaviParams params = new AmapNaviParams(new Poi("", null, ""),
                        null, new Poi(remind.getLocation(), latLng, ""), AmapNaviType.DRIVER);
                params.setUseInnerVoice(true);
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params,
                        FallPositionActivity.this);
                break;
            case R.id.ivCall:
                if (!TextUtils.isEmpty(remind.getPhone())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + remind.getPhone());
                    intent.setData(data);
                    startActivity(intent);
                } else {
                    ToastUtil.showShort("不支持的操作");
                }
                break;
        }
    }

    private void render(Marker marker, View view) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvNav = view.findViewById(R.id.tvNav);
        ImageView ivImage = view.findViewById(R.id.ivImage);
        ImageView ivCall = view.findViewById(R.id.ivCall);
        if (type == 0) {
            tvTitle.setText(remind.getRelation() + "佩戴的衣带保在此处触发！");
        } else if (type == 2) {
            tvTitle.setText(remind.getRelation() + "已离开活动范围，请尽快处理！");
        } else {
            tvTitle.setText("感应到佩戴者运动幅度可能较大");
        }
        tvName.setText(remind.getNickName());
        tvPhone.setText(remind.getPhone());
        tvAddress.setText(remind.getLocation());
        tvTime.setText(remind.getCreateDate());
        Glide.with(this)
                .load(remind.getPortrait())
                .placeholder(R.mipmap.ic_default_portrait)
                .error(R.mipmap.ic_default_portrait)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivImage);

        ivCall.setOnClickListener(this);
        tvNav.setOnClickListener(this);

//        //地理位置逆编码
//        LatLonPoint point = new LatLonPoint(latLng.latitude, latLng.longitude);
//        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
//        RegeocodeQuery query = new RegeocodeQuery(point, 100, GeocodeSearch.AMAP);
//        geocodeSearch.getFromLocationAsyn(query);
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
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
