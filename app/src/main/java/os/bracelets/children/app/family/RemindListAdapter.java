package os.bracelets.children.app.family;

import android.support.annotation.Nullable;

import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import os.bracelets.children.R;
import os.bracelets.children.bean.Remind;
import os.bracelets.children.utils.AppUtils;

public class RemindListAdapter extends BaseQuickAdapter<Remind, BaseViewHolder> {

    public RemindListAdapter(@Nullable List<Remind> data) {
        super(R.layout.item_remind_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Remind item) {
        helper.setText(R.id.tvTitle, "提醒标题：" + item.getRemindTitle());
        helper.setText(R.id.tvTime, "提醒时间：" + item.getRemindTime());
        helper.setText(R.id.tvDate, "提醒周期：" + AppUtils.getRemindDate(item.getRemindPeriod()));
        helper.setText(R.id.tvContent, "提醒内容：" + item.getRemindContent());
    }
}
