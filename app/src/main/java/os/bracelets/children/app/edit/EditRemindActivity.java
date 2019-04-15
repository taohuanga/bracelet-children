package os.bracelets.children.app.edit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

import aio.health2world.http.HttpResult;
import aio.health2world.pickeview.TimePickerView;
import aio.health2world.utils.Logger;
import aio.health2world.utils.TimePickerUtil;
import aio.health2world.utils.ToastUtil;
import aio.health2world.view.LoadingDialog;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.bean.Remind;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.AppUtils;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class EditRemindActivity extends BaseActivity implements TimePickerView.OnTimeSelectListener {

    private TitleBar titleBar;

    private FamilyMember member;
    private Remind remind;

    private EditText edTitle, edContent;
    private TextView tvCycle, tvTime;
    private Button btnSave;

    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    private TimePickerView pickerView;
    private String[] date = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private boolean[] checked = {false, false, false, false, false, false, false};
    private String remindPeriod = "";
    private String remindTime = "";
    private AlertDialog alertDialog;
    private LinearLayout llRemindTime, llRemindCycle;

    private LoadingDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_remind;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        edTitle = findView(R.id.edTitle);
        edContent = findView(R.id.edContent);
        tvCycle = findView(R.id.tvCycle);
        tvTime = findView(R.id.tvTime);
        btnSave = findView(R.id.btnSave);
        llRemindTime = findView(R.id.llRemindTime);
        llRemindCycle = findView(R.id.llRemindCycle);
    }

    @Override
    protected void initData() {
        dialog = new LoadingDialog(this);
        if (getIntent().hasExtra("remind"))
            remind = (Remind) getIntent().getSerializableExtra("remind");
        member = (FamilyMember) getIntent().getSerializableExtra("member");
        pickerView = TimePickerUtil.initTime(this, this);
        if (remind != null) {
            TitleBarUtil.setAttr(this, "", "编辑提醒", titleBar);
            remindPeriod = remind.getRemindPeriod();
            edTitle.setText(remind.getRemindTitle());
            edContent.setText(remind.getRemindContent());
            tvCycle.setText(AppUtils.getRemindDate(remind.getRemindPeriod()));
            tvTime.setText(remind.getRemindTime());
        } else {
            TitleBarUtil.setAttr(this, "", "设置提醒", titleBar);
        }
    }

    @Override
    protected void initListener() {
        btnSave.setOnClickListener(this);
        llRemindCycle.setOnClickListener(this);
        llRemindTime.setOnClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                remind();
                break;
            case R.id.llRemindCycle:
                if (alertDialog == null)
                    alertDialog = new AlertDialog.Builder(this)
                            .setTitle("选择周期")
                            .setMultiChoiceItems(date, checked, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    remindPeriod = "";
                                    remindTime = "";
                                    for (int i = 0; i < checked.length; i++) {
                                        if (checked[i])
                                            remindPeriod += (i + 1) + ";";
                                        if (i == 0 && checked[i])
                                            remindTime += "周一" + " ";
                                        if (i == 1 && checked[i])
                                            remindTime += "周二" + " ";
                                        if (i == 2 && checked[i])
                                            remindTime += "周三" + " ";
                                        if (i == 3 && checked[i])
                                            remindTime += "周四" + " ";
                                        if (i == 4 && checked[i])
                                            remindTime += "周五" + " ";
                                        if (i == 5 && checked[i])
                                            remindTime += "周六" + " ";
                                        if (i == 6 && checked[i])
                                            remindTime += "周日";
                                    }
                                    tvCycle.setText(remindTime);
                                }
                            })
                            .create();
                alertDialog.show();
                break;
            case R.id.llRemindTime:
                pickerView.show();
                break;
        }
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        String time = format.format(date);
        tvTime.setText(time);
    }

    private void remind() {
        String title = edTitle.getText().toString();
        String content = edContent.getText().toString();
        String cycle = tvCycle.getText().toString();
        String time = tvTime.getText().toString();

        if (TextUtils.isEmpty(title)) {
            ToastUtil.showShort("标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showShort("内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(cycle)) {
            ToastUtil.showShort("请设置提醒周期");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            ToastUtil.showShort("请设置提醒时间");
            return;
        }
        remindPeriod = remindPeriod.substring(0, remindPeriod.length() - 1);
        if (remind == null) {
            ApiRequest.remind(String.valueOf(member.getAccountId()), title, content, remindPeriod, time,
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
                                ToastUtil.showShort("操作成功");
                                setResult(RESULT_OK);
                                EditRemindActivity.this.finish();
                            }
                        }
                    });
        } else {
            ApiRequest.editRemind(remind.getRemindId(), title, content, remindPeriod, time, new HttpSubscriber() {

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
                        ToastUtil.showShort("操作成功");
                        setResult(RESULT_OK);
                        EditRemindActivity.this.finish();
                    }
                }
            });
        }
    }
}
