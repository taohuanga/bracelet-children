package os.bracelets.children.app.edit;

import android.support.annotation.Nullable;

import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import os.bracelets.children.R;
import os.bracelets.children.bean.EleFence;

public class EleFenceListAdapter extends BaseQuickAdapter<EleFence, BaseViewHolder> {

    public EleFenceListAdapter(@Nullable List<EleFence> data) {
        super(R.layout.item_elefence_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EleFence item) {
        helper.setText(R.id.tvLocation, item.getLocation());
        helper.setText(R.id.tvScope, "范围" + item.getFenceScope() + "m");
    }
}
