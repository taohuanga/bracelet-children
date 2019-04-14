package os.bracelets.children.app.family;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.http.HttpResult;
import aio.health2world.view.LoadingDialog;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.app.edit.EditRemindActivity;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.Remind;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class RemindListActivity extends BaseActivity {

    private TitleBar titleBar;

    private RecyclerView recyclerView;

    private FamilyMember member;

    private LoadingDialog dialog;

    private List<Remind> remindList;

    private RemindListAdapter remindListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "提醒列表", titleBar);

        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");

        dialog = new LoadingDialog(this);

        remindList = new ArrayList<>();
        remindListAdapter = new RemindListAdapter(remindList);
        recyclerView.setAdapter(remindListAdapter);

        getRemindList();
    }

    @Override
    protected void initListener() {
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleBar.addAction(new TitleBar.TextAction("设置提醒") {
            @Override
            public void performAction(View view) {
                Intent intent = new Intent(RemindListActivity.this, EditRemindActivity.class);
                intent.putExtra("member", member);
                startActivityForResult(intent, 0x01);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 0x01) {
            getRemindList();
        }
    }

    private void getRemindList() {
        ApiRequest.remindList(String.valueOf(member.getAccountId()), new HttpSubscriber() {
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
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        JSONArray array = object.getJSONArray("list");
                        if (array != null) {
                            List<Remind> list = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.optJSONObject(i);
                                Remind bean = Remind.parseBean(obj);
                                list.add(bean);
                            }
                            remindList.clear();
                            remindList.addAll(list);
                            remindListAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
    }
}
