package os.bracelets.children.app.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import aio.health2world.glide_transformations.CropCircleTransformation;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;

public class HomeTopAdapter extends RecyclerView.Adapter<HomeTopAdapter.ViewHolder> {

    private Context mContext;

    private onItemClick clickCb;

    private List<FamilyMember> familyMemberList;

    public HomeTopAdapter(Context mContext, onItemClick clickCb, List<FamilyMember> familyMemberList) {
        this.mContext = mContext;
        this.clickCb = clickCb;
        this.familyMemberList = familyMemberList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_home_top, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (i == 0) {
            viewHolder.img.setImageResource(R.drawable.grzx_03);
            viewHolder.tvName.setText("");
            viewHolder.flowLayout.removeAllViews();
        } else {
            FamilyMember member = familyMemberList.get(i);
            Glide.with(mContext)
                    .load(member.getProfile())
                    .placeholder(R.mipmap.ic_default_portrait)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(viewHolder.img);

            viewHolder.tvName.setText(member.getRealName());
            viewHolder.flowLayout.removeAllViews();
            String labels = member.getLabels();
            final List<String> labelArray = new ArrayList<>();
            if (!TextUtils.isEmpty(labels)) {
                String[] label = labels.split(";");
                for (String s : label) {
                    labelArray.add(s.split(",")[2]);
                }
                viewHolder.flowLayout.setAdapter(new TagAdapter(labelArray) {
                    @Override
                    public View getView(FlowLayout flowLayout, int i, Object o) {
                        TextView view = new TextView(mContext);
                        view.setTextSize(13f);
                        view.setPadding(4, 4, 4, 4);
                        view.setTextColor(mContext.getResources().getColor(R.color.white));
                        view.setBackgroundResource(R.drawable.shape_tag_text_bg);
                        view.setText(labelArray.get(i));
                        return view;
                    }
                });
            }
            if (member.isChecked()) {
                viewHolder.flowLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder.flowLayout.setVisibility(View.INVISIBLE);
            }
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCb != null) {
                    clickCb.clickItem(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return familyMemberList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        TagFlowLayout flowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tvName);
            flowLayout = itemView.findViewById(R.id.flowLayout);
        }
    }

    interface onItemClick {
        void clickItem(int pos);
    }

    interface onItemLongClick {
        void clickLongItem(int pos);
    }
}
