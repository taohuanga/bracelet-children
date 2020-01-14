package os.bracelets.children.app.personal;

import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aio.health2world.utils.ToastUtil;
import os.bracelets.children.R;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * Created by lishiyou on 2019/3/14.
 */

public class InputMsgActivity extends BaseActivity {

    public static final String KEY = "msg";
    public static final String TYPE = "type";

    private TitleBar titleBar;

    private EditText editText;

    private String title = "";

    private int type = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_msg;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "", titleBar);
        editText = findView(R.id.editText);
    }

    @Override
    protected void initData() {
        if (getIntent().hasExtra(KEY)) {
            title = getIntent().getStringExtra(KEY);
            titleBar.setTitle(title);
        }
        if (getIntent().hasExtra(TYPE))
            type = getIntent().getIntExtra(TYPE, -1);

        if (type == PersonalMsgActivity.ITEM_HEIGHT || type == PersonalMsgActivity.ITEM_WEIGHT
                || type == PersonalMsgActivity.ITEM_PHONE) {
            editText.setFilters(new InputFilter[]{numberFilter, new InputFilter.LengthFilter(5)});
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (type == PersonalMsgActivity.ITEM_PHONE) {
            editText.setFilters(new InputFilter[]{numberFilter, new InputFilter.LengthFilter(11)});
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }

        if (type == PersonalMsgActivity.ITEM_RELATION) {
            //这里输入自己想要的提示文字
            SpannableString s = new SpannableString(getString(R.string.input_relation_added));
            editText.setHint(s);
        }

        if (type == PersonalMsgActivity.ITEM_NAME) {
            //这里输入自己想要的提示文字
            findView(R.id.tvDescription).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.addAction(new TitleBar.TextAction(getString(R.string.sure)) {
            @Override
            public void performAction(View view) {
                save();
            }
        });
    }

    private void save() {
        String msg = editText.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(getString(R.string.input_content));
            return;
        }
        if (type == PersonalMsgActivity.ITEM_PHONE) {

            if(msg.length()!=11){
                ToastUtil.showShort(getString(R.string.phone_incorrect));
                return;
            }

//            if (!MatchUtil.isChinaPhoneLegal(msg)) {
//                ToastUtil.showShort(getString(R.string.phone_incorrect));
//                return;
//            }
        }
        Intent intent = new Intent();
        intent.putExtra("data", msg);
        setResult(RESULT_OK, intent);
        finish();
    }


    private InputFilter numberFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern p = Pattern.compile("[.0-9]");
            Matcher m = p.matcher(source.toString());
            if (!m.matches()) return "";
            return null;
        }
    };


}
