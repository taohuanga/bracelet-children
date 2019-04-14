package os.bracelets.children.app.edit;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.ToastUtil;
import aio.health2world.view.LoadingDialog;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

public class LabelAddActivity extends BaseActivity {

    private TitleBar titleBar;

    private EditText editText;

    private Button btnSure;

    private LoadingDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_label_add;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        editText = findView(R.id.editText);
        btnSure = findView(R.id.btnSure);
    }

    @Override
    protected void initData() {
        dialog = new LoadingDialog(this);
        TitleBarUtil.setAttr(this, "", "添加标签", titleBar);
    }

    @Override
    protected void initListener() {
        btnSure.setOnClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnSure) {
            addLabel();
        }
    }

    private void addLabel() {
        String label = editText.getText().toString().trim();
        if (TextUtils.isEmpty(label)) {
            ToastUtil.showShort("标签不能为空");
            return;
        }

        ApiRequest.tagAdd(label, new HttpSubscriber() {
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
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
