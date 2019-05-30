package com.newversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.sidebar.ContactsAdapter;
import com.payencai.library.view.CircleImageView;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/29 16:22
 * 邮箱：771548229@qq..com
 */
public class TagUserAdapter extends BaseAdapter {
    Context mContext;

    public TagUserAdapter(Context context, List<ContactModel> contactModels) {
        mContext = context;
        mContactModels = contactModels;
    }
    private OnDelClickListener onDelClickListener;
    public void setOnDelClickListener(OnDelClickListener onDelClickListener){
        this.onDelClickListener=onDelClickListener;
    }
    public interface OnDelClickListener{
        void onClick(int position);
    }
    List<ContactModel> mContactModels;
    @Override
    public int getCount() {
        return mContactModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_tag_user,null);
        CircleImageView circleImageView= (CircleImageView) convertView.findViewById(R.id.head);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        Glide.with(mContext).load(mContactModels.get(position).getHeadPortrait()).into(circleImageView);
        Button btnDelete=convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelClickListener.onClick(position);
            }
        });
        tv_name.setText(mContactModels.get(position).getName());
        return convertView;
    }
}
