package com.example.yunchebao.drive.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.yunchebao.drive.model.SubstitubeComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.payencai.library.view.CircleImageView;
import com.vipcenter.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/4/12 16:41
 * 邮箱：771548229@qq..com
 */
public class SubstitubeAdapter extends BaseAdapter {
    Context mContext;
    List<SubstitubeComment> mDriveMEN;
    PhotoAdapter mPhotoAdapter;
    List<String> images;
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_drive_comment,null);
        SubstitubeComment driveMan=mDriveMEN.get(position);
        CircleImageView iv_head= (CircleImageView) convertView.findViewById(R.id.userhead);
        CircleImageView coashhead= (CircleImageView) convertView.findViewById(R.id.coashhead);
        GridView gv_photo= (GridView) convertView.findViewById(R.id.gv_photo);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_coashname= (TextView) convertView.findViewById(R.id.tv_coashname);
        TextView tv_time= (TextView) convertView.findViewById(R.id.tv_time);
        SimpleRatingBar simpleRatingBar= (SimpleRatingBar) convertView.findViewById(R.id.sb_score);
        TextView tv_content= (TextView) convertView.findViewById(R.id.tv_content);
        tv_name.setText(driveMan.getName());
        tv_coashname.setText(driveMan.getDriverName());
        tv_content.setText(driveMan.getContent());
        tv_time.setText(driveMan.getCreateTime().substring(0,10));
        simpleRatingBar.setRating((float) driveMan.getDriverScore());
        images=new ArrayList<>();
        if(!TextUtils.isEmpty(driveMan.getImgs())){
            if(driveMan.getImgs().contains(",")){
                String[] img=driveMan.getImgs().split(",");
                for (int i = 0; i <img.length ; i++) {
                    images.add(img[i]);
                }
            }else{
                images.add(driveMan.getImgs());
            }
        }
        mPhotoAdapter=new PhotoAdapter(mContext,images);
        gv_photo.setAdapter(mPhotoAdapter);
        Glide.with(mContext).load(driveMan.getDriverHeadPortrait()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(coashhead);
        Glide.with(mContext).load(driveMan.getHeadPortrait()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(iv_head);
        return convertView;
    }
}
