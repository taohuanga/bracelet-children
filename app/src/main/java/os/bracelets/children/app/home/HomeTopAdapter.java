package os.bracelets.children.app.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
        Glide.with(mContext)
                .load(familyMemberList.get(i).getProfile())
                .placeholder(R.mipmap.ic_default_portrait)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(viewHolder.img);

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

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView);
        }
    }

    interface onItemClick {
        void clickItem(int pos);
    }
}
