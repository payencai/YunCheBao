package com.example.yunchebao.mytag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yunchebao.R;
import com.example.yunchebao.sidebar.indexablerv.IndexableAdapter;
import com.payencai.library.util.ToastUtil;

import java.util.List;


/**
 * Created by YoKey on 16/10/8.
 */
public class ContactAdapter extends IndexableAdapter<UserEntity> {
    private LayoutInflater mInflater;

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_choose_user, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle, List<Object> payloads) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, UserEntity entity, List<Object> payloads) {
        ContentVH vh = (ContentVH) holder;
        if(payloads.isEmpty()){
            vh.tvName.setText(entity.getNick());
            RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                    .skipMemoryCache(true);//不做内存缓存
            mRequestOptions.error(R.mipmap.ic_default_head);
            mRequestOptions.placeholder(R.mipmap.ic_default_head);
            Glide.with(holder.itemView.getContext()).load(entity.getHeadPortrait()).apply(mRequestOptions).into(vh.ivAvatar);
            if (entity.isSelect()) {
                vh.ivChoose.setImageResource(R.mipmap.select);
            } else {
                vh.ivChoose.setImageResource(R.mipmap.unselect);
            }
        }else{
            if (entity.isSelect()) {
                vh.ivChoose.setImageResource(R.mipmap.select);
            } else {
                vh.ivChoose.setImageResource(R.mipmap.unselect);
            }
        }
        if(entity.isShow()){
            vh.ivChoose.setVisibility(View.VISIBLE);
        }else{
            vh.ivChoose.setVisibility(View.GONE);
        }


    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivAvatar;
        ImageView ivChoose;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            ivChoose = (ImageView) itemView.findViewById(R.id.iv_choose);
        }
    }
}
