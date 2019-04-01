package os.bracelets.children.app.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;

import aio.health2world.glide_transformations.CropCircleTransformation;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class FallPositionActivity extends AppCompatActivity implements AMap.InfoWindowAdapter {

    private TitleBar titleBar;

    private AMap aMap;

    private MapView mapView;

    private LatLng latLng = new LatLng(22.657569, 114.064304);

    private FamilyMember member;

    private RemindBean remind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_position);

        titleBar = findViewById(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "跌倒位置", titleBar);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        initData();

        addListener();
    }

    private void initData() {

        member = (FamilyMember) getIntent().getSerializableExtra("member");
        remind = (RemindBean) getIntent().getSerializableExtra("remind");

        if (aMap == null)
            aMap = mapView.getMap();
        changeCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(latLng, 18, 30, 30)));
        aMap.clear();
        aMap.setInfoWindowAdapter(this);
        aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .infoWindowEnable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                .showInfoWindow();
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
        render(marker, infoWindow);
        return infoWindow;
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

        tvTitle.setText("您的" + member.getRelationship() + "在此处跌倒，请尽快前往处理！");
        tvName.setText(member.getNickName());
        tvPhone.setText(member.getPhone());
        tvTime.setText(remind.getCreateDate());
        Glide.with(this)
                .load(member.getProfile())
                .placeholder(R.mipmap.ic_default_portrait)
                .error(R.mipmap.ic_default_portrait)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivImage);
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
}
