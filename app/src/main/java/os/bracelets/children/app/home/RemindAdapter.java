package os.bracelets.children.app.home;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import os.bracelets.children.R;
import os.bracelets.children.bean.RemindBean;

/**
 * Created by lishiyou on 2019/1/27.
 */

public class RemindAdapter extends BaseQuickAdapter<RemindBean, BaseViewHolder> {


    public RemindAdapter(@Nullable List<RemindBean> data) {
        super(R.layout.item_remind_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RemindBean item) {
        helper.setText(R.id.tvTime, item.getCreateDate());
        helper.setText(R.id.tvContent, item.getContent());

//        if (!TextUtils.isEmpty(item.getContent())) {
//            String[] array = item.getContent().split("###");
//            helper.setText(R.id.tvContent, array[0]);
//        }
    }
}
