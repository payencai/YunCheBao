package com.baike.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baike.model.BaikeItem;
import com.bumptech.glide.Glide;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/28 16:06
 * 邮箱：771548229@qq..com
 */
public class BaikeItemAdapter extends RecyclerView.Adapter<BaikeItemAdapter.BaikeViewHolder>{
    public List<BaikeItem> mBaikeItems;

    public BaikeItemAdapter(List<BaikeItem> baikeItems) {
        mBaikeItems = baikeItems;

    }
    private BaikeItemAdapter.OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onClick(int position);

    }
    public void setOnItemClickListener(BaikeItemAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public BaikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_baike, null);
        return new BaikeItemAdapter.BaikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaikeViewHolder holder, final int position) {
        final BaikeItem baikeItem = mBaikeItems.get(position);
        holder.tv_time.setText(baikeItem.getCreateTime());
        holder.tv_title.setText(baikeItem.getTitle());
        holder.tv_content.setText(baikeItem.getSynopsis());
        Glide.with(holder.itemView.getContext()).load(baikeItem.getPicture()).into(holder.ivAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBaikeItems.size();
    }

    class BaikeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tv_title;
        TextView tv_time;
        TextView tv_content;
        BaikeViewHolder(View itemView) {
            super(itemView);

            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
