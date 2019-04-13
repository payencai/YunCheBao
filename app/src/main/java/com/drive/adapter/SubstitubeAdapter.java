package com.drive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.drive.model.DriveMan;
import com.drive.model.SubstitubeComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.payencai.library.view.CircleImageView;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/12 16:41
 * 邮箱：771548229@qq..com
 */
public class SubstitubeAdapter extends BaseAdapter {
    Context mContext;
    List<SubstitubeComment> mDriveMEN;

    public SubstitubeAdapter(Context context, List<SubstitubeComment> driveMEN) {
        mContext = context;
        mDriveMEN = driveMEN;
    }

    @Override
    public int getCount() {
        return mDriveMEN.size();
    }

    @Override
    public Object getItem(int position) {
        return mDriveMEN.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    OnSelectListener mOnSelectListener;
    public interface OnSelectListener{
        void onSelect(int position);
    }

    public OnSelectListener getOnSelectListener() {
        return mOnSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_coash_comment,null);
        SubstitubeComment driveMan=mDriveMEN.get(position);
        CircleImageView iv_head= (CircleImageView) convertView.findViewById(R.id.userhead);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_time= (TextView) convertView.findViewById(R.id.tv_time);
        SimpleRatingBar simpleRatingBar= (SimpleRatingBar) convertView.findViewById(R.id.sb_score);
        TextView tv_content= (TextView) convertView.findViewById(R.id.tv_content);
        tv_name.setText(driveMan.getName());
        Glide.with(mContext).load(driveMan.getDriverHeadPortrait()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(iv_head);
        return convertView;
    }
}
