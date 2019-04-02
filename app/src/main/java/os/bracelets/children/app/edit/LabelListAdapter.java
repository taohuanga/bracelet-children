package os.bracelets.children.app.edit;

import android.support.annotation.Nullable;

import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseSectionQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import os.bracelets.children.R;
import os.bracelets.children.bean.LabelBean;
import os.bracelets.children.bean.LabelSection;

public class LabelListAdapter extends BaseSectionQuickAdapter<LabelSection, BaseViewHolder> {

    public LabelListAdapter(@Nullable List<LabelSection> data) {
        super(R.layout.item_label_layout, R.layout.item_label_type, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, LabelSection item) {
        helper.setText(R.id.tvTitle, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, LabelSection item) {
        helper.setText(R.id.tvTagName, item.t.getLabelName());
        if (item.t.isChecked()) {
            helper.setBackgroundRes(R.id.tvTagName, R.drawable.shape_tag_select);
            helper.setTextColor(R.id.tvTagName, mContext.getResources().getColor(R.color.white));
        } else {
            helper.setBackgroundRes(R.id.tvTagName, R.drawable.shape_edit_text_bg);
            helper.setTextColor(R.id.tvTagName, mContext.getResources().getColor(R.color.black6));
        }

    }
}
