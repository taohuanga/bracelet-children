package os.bracelets.children.app.contact;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.rx.rxpermissions.RxPermissions;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.ContactBean;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.MVPBaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * 一键拨号
 * Created by lishiyou on 2019/2/24.
 */

public class ContactActivity extends MVPBaseActivity<ContactContract.Presenter> implements ContactContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    private TitleBar titleBar;

    private ContactAdapter contactAdapter;

    private SwipeRefreshLayout refreshLayout;

    private RecyclerView recyclerView;

    private List<ContactBean> personList;

    private int pageNo = 1;

    private RxPermissions rxPermissions;

    private FamilyMember member;

    private int delPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        refreshLayout = findView(R.id.refreshLayout);
        recyclerView = findView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        TitleBarUtil.setAttr(this, "", "联系人列表", titleBar);
        rxPermissions = new RxPermissions(this);
        refreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.appThemeColor));
        personList = new ArrayList<>();
        contactAdapter = new ContactAdapter(personList);
        recyclerView.setAdapter(contactAdapter);

        contactAdapter.bindToRecyclerView(recyclerView);
        contactAdapter.setEmptyView(R.layout.layout_empty_view);

        member = (FamilyMember) getIntent().getSerializableExtra("member");

        onRefresh();
    }

    @Override
    protected ContactContract.Presenter getPresenter() {
        return new ContactPresenter(this);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
        contactAdapter.setOnLoadMoreListener(this, recyclerView);
        contactAdapter.setOnItemChildClickListener(this);
        contactAdapter.setOnItemClickListener(this);
        contactAdapter.setOnItemLongClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.addAction(new TitleBar.TextAction("添加联系人") {
            @Override
            public void performAction(View view) {
                Intent addIntent = new Intent(ContactActivity.this, ContactAddActivity.class);
                addIntent.putExtra("member", member);
                startActivityForResult(addIntent, 0x01);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 0x01) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        refreshLayout.setRefreshing(true);
        mPresenter.contactList(pageNo, String.valueOf(member.getAccountId()));
    }

    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        mPresenter.contactList(pageNo, String.valueOf(member.getAccountId()));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ContactBean contact = (ContactBean) adapter.getItem(position);
        Intent addIntent = new Intent(ContactActivity.this, ContactAddActivity.class);
        addIntent.putExtra("member", member);
        addIntent.putExtra("contact", contact);
        startActivityForResult(addIntent, 0x01);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ContactBean contact = (ContactBean) adapter.getItem(position);
        String phone = contact.getPhone();
        if (TextUtils.isEmpty(phone))
            return;
        callPhone(phone);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        delPosition = position;
        final ContactBean contact = (ContactBean) adapter.getItem(position);
        new AlertDialog.Builder(this)
                .setMessage("是否需要删除该联系人？")
                .setNegativeButton(getString(R.string.pickerview_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.sure1), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.contactDelete(String.valueOf(contact.getContactId()));
                    }
                })
                .create()
                .show();
        return false;
    }

    @Override
    public void deleteSuccess() {
        personList.remove(delPosition);
        contactAdapter.notifyItemRemoved(delPosition);
    }

    @Override
    public void loadContactSuccess(List<ContactBean> contactList) {
        refreshLayout.setRefreshing(false);
        if (pageNo == 1)
            personList.clear();
        personList.addAll(contactList);
        contactAdapter.notifyDataSetChanged();
        if (contactList.size() >= AppConfig.PAGE_SIZE)
            contactAdapter.loadMoreComplete();
        else
            contactAdapter.loadMoreEnd();
    }

    @Override
    public void loadContactError() {
        if (pageNo > 1)
            pageNo--;
        refreshLayout.setRefreshing(false);
    }

    private void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
//        rxPermissions.request(Manifest.permission.CALL_PHONE)
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        if (aBoolean) {
//
//                        } else {
//                            ToastUtil.showShort("相关权限被拒绝");
//                        }
//                    }
//                });
    }
}
