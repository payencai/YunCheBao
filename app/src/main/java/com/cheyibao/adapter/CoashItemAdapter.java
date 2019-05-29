package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cheyibao.model.CoachItem;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class CoashItemAdapter extends BaseAdapter{
    private List<CoachItem> mClassItems;
    private Context mContext;

    public CoashItemAdapter(Context context, List<CoachItem> classItems) {
        mClassItems = classItems;
        mContext = context;
    }
    private OnSelectListener mOnSelectListener;
    public interface OnSelectListener{
        void onSelect(int position);
    }
    public void setOnSelectListener(OnSelectListener mOnSelectListener){
        this.mOnSelectListener=mOnSelectListener;
    }
    public List<CoachItem> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<CoachItem> classItems) {
        mClassItems = classItems;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mClassItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CoachItem coachItem=mClassItems.get(position);
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_coash,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        TextView tv_select= (TextView) view.findViewById(R.id.tv_select);
        TextView tv_rate= (TextView) view.findViewById(R.id.tv_rate);
        SimpleRatingBar simpleRatingBar= (SimpleRatingBar) view.findViewById(R.id.sr_score);
        ImageView iv_head= (ImageView) view.findViewById(R.id.iv_head);
        tv_name.setText(coachItem.getCoachName());
        int num=position;
        tv_rate.setText(++num+"");
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSelectListener.onSelect(position);
            }
        });

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Glide.with(mContext).load(coachItem.getCoachHead()).apply(mRequestOptions).into(iv_head);
        simpleRatingBar.setRating(coachItem.getScore());
        return view;
    }
}
