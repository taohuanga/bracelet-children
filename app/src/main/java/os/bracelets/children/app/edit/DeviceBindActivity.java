package os.bracelets.children.app.edit;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aio.health2world.http.HttpResult;
import aio.health2world.qrcode.CaptureActivity;
import aio.health2world.utils.ToastUtil;
import aio.health2world.view.LoadingDialog;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * 设备绑定
 */
public class DeviceBindActivity extends BaseActivity {

    private TitleBar titleBar;
    private ImageView ivScan;
    private EditText edDeviceNo;
    private TextView tvName;
    private Button btnBind;
    private FamilyMember member;

    private LoadingDialog dialog;
    //0 绑定  1 解绑
    private int bindType = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_bind;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        ivScan = findView(R.id.ivScan);
        TitleBarUtil.setAttr(this, "", "绑定设备", titleBar);

        edDeviceNo = findView(R.id.edDeviceNo);
        tvName = findView(R.id.tvName);
        btnBind = findView(R.id.btnBind);
    }

    @Override
    protected void initData() {
        edDeviceNo.setTransformationMethod(new UpperCaseTransform());
        member = (FamilyMember) getIntent().getSerializableExtra("member");
//        tvName.setText(member.getNickName());
        dialog = new LoadingDialog(this);
        getBindMsg();
    }

    @Override
    protected void initListener() {
        ivScan.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivScan) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, 0x11);
        }
        if (v.getId() == R.id.btnBind) {
            String deviceNo = edDeviceNo.getText().toString().trim();
            if (TextUtils.isEmpty(deviceNo)) {
                ToastUtil.showShort("请输入设备编号");
                return;
            }
            deviceNo = deviceNo.replace(":", "").toUpperCase();
            if (bindType == 0) {
                bindDevice(deviceNo, "");
            } else {
                unbindDevice(deviceNo);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 0x11) {
            String result = data.getStringExtra("result");
            if (!TextUtils.isEmpty(result))
                edDeviceNo.setText(result);
        }
    }

    private void getBindMsg() {
        dialog.show();
        ApiRequest.deviceBindInfo(member.getAccountId(), new HttpSubscriber() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                if (result.code.equals(AppConfig.SUCCESS)) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(result.data));
                        String macAddress = object.optString("macAddress");
                        if (!TextUtils.isEmpty(macAddress)) {
                            macAddress = macAddress.replace(":", "").toUpperCase();
                            edDeviceNo.setText(macAddress);
                            edDeviceNo.setSelection(edDeviceNo.getText().length());
                        }
                        bindType = 1;
                        btnBind.setText("解绑该设备");
                        btnBind.setBackgroundResource(R.drawable.shape_unbind_device_bg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private void bindDevice(String deviceNo, String bindTimes) {
        if (deviceNo.length() != 12) {
            ToastUtil.showShort("请输入正确的mac地址");
            return;
        }
        ApiRequest.bindDevice(String.valueOf(member.getAccountId()), deviceNo, bindTimes,
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
//                        super.onNext(result);
                        dialog.dismiss();
                        if (result.code.equals(AppConfig.SUCCESS)) {
                            ToastUtil.showShort("操作成功");
                            finish();
                        }
                        //设备已被其他用户绑定
                        if (result.code.equals("006")) {
                            new AlertDialog.Builder(DeviceBindActivity.this)
                                    .setMessage("该设备已被其他用户绑定，是否强制绑定该设备？")
                                    .setNegativeButton("取消绑定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setPositiveButton("强制绑定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String deviceNo = edDeviceNo.getText().toString().trim();
                                            deviceNo = deviceNo.replace(":", "").toUpperCase();
                                            bindDevice(deviceNo, "1");
                                        }
                                    })
                                    .create()
                                    .show();

                        }
                    }
                });
    }

    private void unbindDevice(final String deviceNo) {
        dialog.show();
        ApiRequest.deviceUnbind(member.getAccountId(), deviceNo, new HttpSubscriber() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                if (result.code.equals(AppConfig.SUCCESS)) {
                    ToastUtil.showShort("操作成功");
                    edDeviceNo.getText().clear();
                    bindType = 0;
                    btnBind.setText("绑定设备");
                    btnBind.setBackgroundResource(R.drawable.shape_button_bg);
                }
            }
        });
    }

    public class UpperCaseTransform extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog = null;
    }
}
