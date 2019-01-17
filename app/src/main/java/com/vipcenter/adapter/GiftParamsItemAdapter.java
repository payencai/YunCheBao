package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.CoachItem;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.vipcenter.model.GiftParams;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class GiftParamsItemAdapter extends BaseAdapter{
    private List<GiftParams> mClassItems;
    private Context mContext;

    public GiftParamsItemAdapter(Context context, List<GiftParams> classItems) {
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
    public List<GiftParams> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<GiftParams> classItems) {
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

        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_gift_params,null);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_value= (TextView) convertView.findViewById(R.id.tv_value);
        tv_name.setText(mClassItems.get(position).getParamName());
        tv_value.setText(mClassItems.get(position).getParamValue());
        return convertView;
    }
}
