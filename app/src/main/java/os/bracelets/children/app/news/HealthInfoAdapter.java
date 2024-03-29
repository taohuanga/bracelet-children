package os.bracelets.children.app.news;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import aio.health2world.glide_transformations.CropCircleTransformation;
import os.bracelets.children.R;
import os.bracelets.children.bean.HealthInfo;

/**
 * Created by lishiyou on 2019/2/24.
 */

public class HealthInfoAdapter extends BaseQuickAdapter<HealthInfo,BaseViewHolder> {

    public HealthInfoAdapter(@Nullable List<HealthInfo> data) {
        super(R.layout.item_health_info,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthInfo item) {
        helper.setText(R.id.tvTitle,item.getTitle());
        helper.setText(R.id.tvTime,item.getCreateDate());

        ImageView ivImage = helper.getView(R.id.ivImage);

        Glide.with(mContext)
                .load(item.getImageUrl())
                .placeholder(R.mipmap.bg_head)
                .error(R.mipmap.bg_head)
                .into(ivImage);
    }
}
