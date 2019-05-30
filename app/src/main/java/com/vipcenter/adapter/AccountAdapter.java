package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;
import com.tool.AccountUtil;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/7 14:08
 * 邮箱：771548229@qq..com
 */
public class AccountAdapter extends BaseAdapter {
    Context mContext;
    List<AccountUtil.Account> mAccounts;
    private MyPublishListAdapter.onDeleteClickListener mOnDeleteClickListener;
    public void setOnDeleteClickListener(MyPublishListAdapter.onDeleteClickListener mOnDeleteClickListener){
        this.mOnDeleteClickListener=mOnDeleteClickListener;
    }
    public interface onDeleteClickListener{
        void onClick(int pos);
        void onItemClick(int pos);
    }
    public AccountAdapter(Context context, List<AccountUtil.Account> accounts) {
        mContext = context;
        mAccounts = accounts;
    }

    @Override
    public int getCount() {
        return mAccounts.size();
    }

    @Override
    public Object getItem(int position) {
        return mAccounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_account,null);
        TextView tv_current=convertView.findViewById(R.id.tv_current);
        TextView tv_phone=convertView.findViewById(R.id.tv_phone);
        RelativeLayout ll_content= (RelativeLayout) convertView.findViewById(R.id.ll_content);
        Button btnDelete= (Button) convertView.findViewById(R.id.btnDelete);
        CircleImageView iv_head=convertView.findViewById(R.id.iv_head);
        AccountUtil.Account account=mAccounts.get(position);
        if(account.isCurrent()){
            tv_current.setVisibility(View.VISIBLE);
        }else{
            tv_current.setVisibility(View.GONE);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteClickListener.onClick(position);
            }
        });
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteClickListener.onItemClick(position);
            }
        });
        tv_phone.setText(account.getAccount());
        Glide.with(mContext).load(account.getPhoto()).into(iv_head);
        return convertView;
    }
}
