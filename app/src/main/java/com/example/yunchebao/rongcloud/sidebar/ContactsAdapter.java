package com.example.yunchebao.rongcloud.sidebar;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yunchebao.R;

import java.util.List;

/**
 * Contact联系人适配器
 *
 * @author nanchen
 * @fileName WaveSideBarView
 * @packageName com.nanchen.wavesidebarview
 * @date 2016/12/27  15:33
 * @github https://github.com/nanchen2251
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    public List<ContactModel> contacts;
    private static final String TAG = "ContactsAdapter";
    private boolean isShow=true;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    public interface OnItemClickListener{
        void onClick(int position,ImageView imageView);
    }
    public ContactsAdapter(List<ContactModel> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layaout_item_contacts, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        ContactModel contact = contacts.get(position);
        Log.e(TAG, "onBindViewHolder: index:" +contact.getIndex());
        int old=position-1;
        if (position == 0 || !contacts.get(old).getIndex().equals(contact.getIndex())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getIndex());
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
       // Glide.with(mContext).load(coashComment.getHeadPortrait()).apply(mRequestOptions).into(iv_userhead);
        Glide.with(holder.itemView.getContext()).load(contact.getHeadPortrait()).apply(mRequestOptions).into(holder.ivAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position,holder.choose);
            }
        });
        if(contact.isSelect()){
            holder.choose.setImageResource(R.mipmap.select);
        }else{
            holder.choose.setImageResource(R.mipmap.unselect);
        }
        if(isShow()){
            holder.choose.setVisibility(View.VISIBLE);
        }else{
            holder.choose.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;
        ImageView ivAvatar;
        ImageView choose;
        TextView tvName;

        ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            choose = (ImageView) itemView.findViewById(R.id.choose);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
