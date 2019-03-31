package os.bracelets.children.app.family;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

import os.bracelets.children.R;

public class TagListAdapter extends TagAdapter<String> {

    private Context mContext;

    public TagListAdapter(Context context, List<String> data) {
        super(data);
        mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView textView = new TextView(mContext);
        textView.setText(s);
        textView.setTextSize(12f);
        textView.setPadding(4, 4, 4, 4);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(mContext.getResources().getColor(R.color.white));
        textView.setBackgroundResource(R.drawable.shape_tag_text_bg);
        return textView;
    }
}