package com.example.yunchebao.drive.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.yunchebao.drive.model.DriveMan;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/12 16:41
 * 邮箱：771548229@qq..com
 */
public class DriveManAdapter extends BaseAdapter {
    Context mContext;
    List<DriveMan> mDriveMEN;

    public DriveManAdapter(Context context, List<DriveMan> driveMEN) {
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

    public interface OnSelectListener {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_coash, null);
        DriveMan driveMan = mDriveMEN.get(position);
        ImageView iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvgrade = (TextView) convertView.findViewById(R.id.tv_rate);
        SimpleRatingBar simpleRatingBar = (SimpleRatingBar) convertView.findViewById(R.id.sr_score);
        TextView tv_select = (TextView) convertView.findViewById(R.id.tv_select);
        tv_name.setText(driveMan.getName());
        simpleRatingBar.setRating(driveMan.getScore());
        tvgrade.setText(driveMan.getGrade() + "");
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSelectListener.onSelect(position);
            }
        });
        if (TextUtils.isEmpty(driveMan.getHeadPortrait())) {
            iv_head.setImageResource(R.mipmap.ic_default_head);
        } else
            Glide.with(mContext).load(driveMan.getHeadPortrait()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(iv_head);
        return convertView;
    }
}
