package os.bracelets.children.app.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import os.bracelets.children.R;
import os.bracelets.children.app.contact.ContactActivity;
import os.bracelets.children.app.family.EleFenceActivity;
import os.bracelets.children.app.family.TagListActivity;
import os.bracelets.children.app.nearby.NearbyActivity;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {

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
}
