package os.bracelets.children.app.home;

import android.support.annotation.Nullable;

import java.util.List;

import aio.health2world.DataEntity;
import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import os.bracelets.children.R;
import os.bracelets.children.bean.DailySports;

public class SportsListAdapter extends BaseQuickAdapter<DailySports, BaseViewHolder> {

    public SportsListAdapter(@Nullable List<DailySports> data) {
        super(R.layout.item_daily_sports, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailySports item) {
        helper.setText(R.id.tvTime, item.getDailyDay() + " " + item.getDailyTime());
        helper.setText(R.id.tvStepNum, String.valueOf(item.getStepNum()));
    }
}
