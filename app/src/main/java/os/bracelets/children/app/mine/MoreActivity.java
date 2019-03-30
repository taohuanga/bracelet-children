package os.bracelets.children.app.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapRouteActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

import os.bracelets.children.R;
import os.bracelets.children.app.contact.ContactActivity;
import os.bracelets.children.app.family.EleFenceActivity;
import os.bracelets.children.app.family.TagListActivity;
import os.bracelets.children.app.nearby.NearbyActivity;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener , INaviInfoCallback {

    private Button btnNearby, btnNav, btnTag, btnRail, btnContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        initView();
    }

    private void initView() {
        btnNearby = (Button) findViewById(R.id.btnNearby);
        btnNav = (Button) findViewById(R.id.btnNav);
        btnTag = (Button) findViewById(R.id.btnTag);
        btnRail = (Button) findViewById(R.id.btnRail);
        btnContact = (Button) findViewById(R.id.btnContact);


        btnNearby.setOnClickListener(this);
        btnNav.setOnClickListener(this);
        btnTag.setOnClickListener(this);
        btnRail.setOnClickListener(this);
        btnContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNearby:
                startActivity(new Intent(MoreActivity.this, NearbyActivity.class));
                break;
            case R.id.btnNav:
                AmapNaviParams params = new AmapNaviParams(new Poi("", null, ""),
                        null, new Poi("", null, ""), AmapNaviType.DRIVER);
                params.setUseInnerVoice(true);
                AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params,
                        MoreActivity.this, AmapRouteActivity.class);
                break;
            case R.id.btnTag:
                startActivity(new Intent(MoreActivity.this, TagListActivity.class));
                break;
            case R.id.btnRail:
                startActivity(new Intent(MoreActivity.this, EleFenceActivity.class));
                break;
            case R.id.btnContact:
                startActivity(new Intent(MoreActivity.this, ContactActivity.class));
                break;
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
