package os.bracelets.children.app.edit;

import android.support.annotation.Nullable;

import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import os.bracelets.children.R;
import os.bracelets.children.bean.LabelBean;

public class LabelListAdapter extends BaseQuickAdapter<LabelBean, BaseViewHolder> {

    public LabelListAdapter(@Nullable List<LabelBean> data) {
        super(R.layout.item_label_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, LabelBean item) {
        helper.setText(R.id.tvTagName, item.getLabelName());
        if (item.isChecked()) {
            helper.setBackgroundRes(R.id.tvTagName, R.drawable.shape_tag_select);
            helper.setTextColor(R.id.tvTagName,mContext.getResources().getColor(R.color.white));
        } else {
            helper.setBackgroundRes(R.id.tvTagName, R.drawable.shape_edit_text_bg);
            helper.setTextColor(R.id.tvTagName,mContext.getResources().getColor(R.color.black6));
        }

    }
}
