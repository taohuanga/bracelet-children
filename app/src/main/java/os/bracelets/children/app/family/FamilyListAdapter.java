package os.bracelets.children.app.family;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.brvah.BaseQuickAdapter;
import aio.health2world.brvah.BaseViewHolder;
import aio.health2world.glide_transformations.CropCircleTransformation;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;

/**
 * Created by lishiyou on 2019/3/24.
 */

public class FamilyListAdapter extends BaseQuickAdapter<FamilyMember, BaseViewHolder> {

    public FamilyListAdapter(@Nullable List<FamilyMember> data) {
        super(R.layout.item_family_member, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyMember item) {
        helper.setText(R.id.personRelation, item.getRelationship());
        helper.setText(R.id.personName, item.getNickName());

        TagFlowLayout flowLayout = helper.getView(R.id.flowLayout);

        flowLayout.removeAllViews();

        if (!TextUtils.isEmpty(item.getLabels())) {
            List<String> list = new ArrayList<>();
            for (String s : item.getLabels().split(";")) {
                list.add(s);
            }
            flowLayout.setAdapter(new TagListAdapter(mContext, list));
        }

        Glide.with(mContext)
                .load(item.getProfile())
                .placeholder(R.mipmap.ic_default_portrait)
                .error(R.mipmap.ic_default_portrait)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into((ImageView) helper.getView(R.id.personImage));


        helper.addOnClickListener(R.id.imgEdit);
    }

}
