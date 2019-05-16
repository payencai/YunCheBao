package com.example.yunchebao.rongcloud.adapter;

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
import com.example.yunchebao.rongcloud.model.ApplyFriend;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/23 09:54
 * 邮箱：771548229@qq..com
 */
public class ApplyFriendAdapter extends RecyclerView.Adapter<ApplyFriendAdapter.ContactsViewHolder> {

    public List<ApplyFriend> contacts;
    private static final String TAG = "ContactsAdapter";

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, ApplyFriend contactModel);
        void agree(ApplyFriend applyFriend);
        void refuse(ApplyFriend applyFriend);
    }

    public ApplyFriendAdapter(List<ApplyFriend> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ApplyFriendAdapter.ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_apply_friend, null);
        return new ApplyFriendAdapter.ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ApplyFriendAdapter.ContactsViewHolder holder, final int position) {
        final ApplyFriend applyFriend = contacts.get(position);
        holder.tvName.setText(applyFriend.getName());
        holder.tvContent.setText(applyFriend.getApplyReason());
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(holder.itemView.getContext()).load(applyFriend.getHeadPortrait()).apply(mRequestOptions).into(holder.ivAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position,applyFriend);
            }
        });
        holder.tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.agree(applyFriend);
            }
        });
        holder.tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.refuse(applyFriend);
            }
        });
        if(applyFriend.getState()==1){
            holder.tvAgree.setVisibility(View.GONE);
            holder.tvRefuse.setVisibility(View.GONE);
            holder.tvRefused.setVisibility(View.VISIBLE);
            holder.tvRefused.setText("已同意");
        }else if(applyFriend.getState()==0){

        }else{
            holder.tvAgree.setVisibility(View.GONE);
            holder.tvRefuse.setVisibility(View.GONE);
            holder.tvRefused.setVisibility(View.VISIBLE);
            holder.tvRefused.setText("已拒绝");
            holder.tvContent.setText(applyFriend.getRejectReason());
        }


    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvContent;
        TextView tvName;
        TextView tvRefuse;
        TextView tvAgree;
        TextView tvRefused;

        ContactsViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.content);
            ivAvatar = (ImageView) itemView.findViewById(R.id.head);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvRefuse = (TextView) itemView.findViewById(R.id.refuse);
            tvAgree = (TextView) itemView.findViewById(R.id.agree);
            tvRefused = (TextView) itemView.findViewById(R.id.refused);
        }
    }
}