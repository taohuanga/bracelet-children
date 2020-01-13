package os.bracelets.children.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import os.bracelets.children.R;

/**
 * @author lishiyou 自定义首页底部菜单栏
 */
public class HomeTabs extends FrameLayout implements OnClickListener {

    private OnCheckedChangeListener listener;

    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;

    private String[] TITLES;

    public HomeTabs(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_home_tab, this);
        TITLES = context.getResources().getStringArray(R.array.home_tab_data);
        initView();
        addListener();
    }

    private void initView() {
        radioButton0 = (RadioButton) findViewById(R.id.radioButton0);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);

        radioButton0.setText(TITLES[0]);
        radioButton1.setText(TITLES[1]);
        radioButton2.setText(TITLES[2]);
        radioButton3.setText(TITLES[3]);
    }

    private void addListener() {
        radioButton0.setOnClickListener(this);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radioButton0:
                if (listener != null) {
                    listener.onTabChecked(0);
                }
                break;
            case R.id.radioButton1:
                if (listener != null) {
                    listener.onTabChecked(1);
                }
                break;
            case R.id.radioButton2:
                if (listener != null) {
                    listener.onTabChecked(2);
                }
                break;
            case R.id.radioButton3:
                if (listener != null) {
                    listener.onTabChecked(3);
                }
                break;
            default:
                if (listener != null) {
                    listener.onTabChecked(0);
                }
        }
    }

    public interface OnCheckedChangeListener {
        void onTabChecked(int position);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

}
