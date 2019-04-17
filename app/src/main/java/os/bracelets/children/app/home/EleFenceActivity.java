package os.bracelets.children.app.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import os.bracelets.children.R;
import os.bracelets.children.bean.RemindBean;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * 查看电子围栏
 */
public class EleFenceActivity extends AppCompatActivity {

    private TitleBar titleBar;

    private MapView mapView;

    private AMap aMap;

    private RemindBean remind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele_fence);
        titleBar = findViewById(R.id.titleBar);
        mapView = findViewById(R.id.mapView);
        // 此方法必须重写
        mapView.onCreate(savedInstanceState);
        initData();
        initListener();
    }

    protected void initData() {

        TitleBarUtil.setAttr(this,"","查看电子围栏",titleBar);

        remind = (RemindBean) getIntent().getSerializableExtra("remind");

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        double latitude = Double.parseDouble(remind.getLatitude());
        double longitude = Double.parseDouble(remind.getLongitude());
        LatLng latLng = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(latLng, 18, 30, 30)));
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(remind.getLocation())
                .infoWindowEnable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        marker.showInfoWindow();
    }

    protected void initListener() {
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
