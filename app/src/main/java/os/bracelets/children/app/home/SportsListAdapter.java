package os.bracelets.children.app.home;

import android.support.annotation.Nullable;

import java.util.List;

import aio.health2world.DataEntity;
import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;

public class SportsListAdapter extends BaseQuickAdapter<DataEntity, BaseViewHolder> {

    public SportsListAdapter(@Nullable List<DataEntity> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataEntity item) {

    }
}
