package os.bracelets.children.app.edit;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BaseActivity;

public class EditNavActivity extends BaseActivity {

    private RelativeLayout rlSetTag, rlAddTag, rlAddRemind, rlBindDevice, rlAddEle;

    private FamilyMember member;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_nav;
    }

    @Override
    protected void initView() {
        rlSetTag = findView(R.id.rlSetTag);
        rlAddTag = findView(R.id.rlAddTag);
        rlAddRemind = findView(R.id.rlAddRemind);
        rlBindDevice = findView(R.id.rlBindDevice);
        rlAddEle = findView(R.id.rlAddEle);
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");
    }

    @Override
    protected void initListener() {
        rlSetTag.setOnClickListener(this);
        rlAddTag.setOnClickListener(this);
        rlAddRemind.setOnClickListener(this);
        rlBindDevice.setOnClickListener(this);
        rlAddEle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rlSetTag:
                Intent tagIntent = new Intent(this, EditTagActivity.class);
                tagIntent.putExtra("member", member);
                startActivity(tagIntent);
                finish();
                break;
            case R.id.rlAddTag:
                break;
            case R.id.rlAddRemind:
                Intent intent = new Intent(this, EditRemindActivity.class);
                intent.putExtra("member", member);
                startActivity(intent);
                finish();
                break;
            case R.id.rlBindDevice:
                Intent bindIntent = new Intent(this, DeviceBindActivity.class);
                bindIntent.putExtra("member", member);
                startActivity(bindIntent);
                finish();
                break;
            case R.id.rlAddEle:
                break;
        }
    }
}
