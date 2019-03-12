package com.newversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbcircle.data.CarShow;
import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/29 10:35
 * 邮箱：771548229@qq..com
 */
public class CarShowAdapter extends BaseAdapter{
    Context mContext;
    List<CarShow> mCarShows;

    public CarShowAdapter(Context context, List<CarShow> carShows) {
        mContext = context;
        mCarShows = carShows;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public List<CarShow> getCarShows() {
        return mCarShows;
    }

    public void setCarShows(List<CarShow> carShows) {
        mCarShows = carShows;
    }

    @Override
    public int getCount() {
        return mCarShows.size();
    }

    @Override
    public Object getItem(int position) {
        return mCarShows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarShow carShow=mCarShows.get(position);
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_car_show,null);
        ImageView iv_car= (ImageView) convertView.findViewById(R.id.iv_car);
        CircleImageView head= (CircleImageView) convertView.findViewById(R.id.head);
        TextView name= (TextView) convertView.findViewById(R.id.name);
        TextView time= (TextView) convertView.findViewById(R.id.time);
        TextView tv_read= (TextView) convertView.findViewById(R.id.tv_read);
        TextView content= (TextView) convertView.findViewById(R.id.content);
        Glide.with(mContext).load(carShow.getHeadPortrait()).into(head);
        Glide.with(mContext).load(carShow.getImage()).into(iv_car);
        name.setText(carShow.getName());
        tv_read.setText(carShow.getCommentNum()+"");
        time.setText(carShow.getCreateTime());
        content.setText(carShow.getTitle());
        return convertView;
    }
}
