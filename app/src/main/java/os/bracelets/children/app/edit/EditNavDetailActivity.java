package os.bracelets.children.app.edit;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import os.bracelets.children.R;
import os.bracelets.children.app.contact.ContactActivity;
import os.bracelets.children.app.contact.ContactAddActivity;
import os.bracelets.children.app.family.RemindListActivity;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BaseActivity;

public class EditNavDetailActivity extends BaseActivity {

    private RelativeLayout rlContact, rlAddContact, rlSetTag, rlAddTag, rlAddRemind,rlRemindList, rlBindDevice, rlEleList, rlAddEle;

    private FamilyMember member;

    public static final int ITEM_LABEL = 0x01;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_nav_detail;
    }

    @Override
    protected void initView() {
        rlContact = findView(R.id.rlContact);
        rlSetTag = findView(R.id.rlSetTag);
        rlAddTag = findView(R.id.rlAddTag);
        rlAddRemind = findView(R.id.rlAddRemind);
        rlRemindList = findView(R.id.rlRemindList);
        rlBindDevice = findView(R.id.rlBindDevice);
        rlEleList = findView(R.id.rlEleList);
        rlAddEle = findView(R.id.rlAddEle);
        rlAddContact = findView(R.id.rlAddContact);
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");
    }

    @Override
    protected void initListener() {
        rlContact.setOnClickListener(this);
        rlSetTag.setOnClickListener(this);
        rlAddTag.setOnClickListener(this);
        rlAddRemind.setOnClickListener(this);
        rlRemindList.setOnClickListener(this);
        rlBindDevice.setOnClickListener(this);
        rlEleList.setOnClickListener(this);
        rlAddEle.setOnClickListener(this);
        rlAddContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rlContact:
                //亲人的联系人列表
                Intent contactIntent = new Intent(this, ContactActivity.class);
                contactIntent.putExtra("member", member);
                startActivityForResult(contactIntent,ITEM_LABEL);
                finish();
                break;
            case R.id.rlAddContact:
                //添加联系人
                Intent addIntent = new Intent(this, ContactAddActivity.class);
                addIntent.putExtra("member", member);
                startActivity(addIntent);
                finish();
                break;
            case R.id.rlSetTag:
                Intent tagIntent = new Intent(this, LabelEditActivity.class);
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
            case R.id.rlRemindList:
                Intent remindIntent = new Intent(this, RemindListActivity.class);
                remindIntent.putExtra("member", member);
                startActivity(remindIntent);
                finish();
                break;
            case R.id.rlBindDevice:
                Intent bindIntent = new Intent(this, DeviceBindActivity.class);
                bindIntent.putExtra("member", member);
                startActivity(bindIntent);
                finish();
                break;
            case R.id.rlEleList:
                Intent eleListIntent = new Intent(this, EleFenceListActivity.class);
                eleListIntent.putExtra("member", member);
                startActivity(eleListIntent);
                finish();
                break;
            case R.id.rlAddEle:
                Intent eleAddIntent = new Intent(this, EleFenceAddActivity.class);
                eleAddIntent.putExtra("member", member);
                startActivity(eleAddIntent);
                finish();
                break;
        }
    }
}
