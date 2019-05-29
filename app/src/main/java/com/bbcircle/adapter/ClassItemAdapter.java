package com.bbcircle.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbcircle.data.ClassItem;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class ClassItemAdapter extends BaseAdapter{
    private List<ClassItem> mClassItems;
    private Context mContext;

    public ClassItemAdapter( Context context,List<ClassItem> classItems) {
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
    public List<ClassItem> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<ClassItem> classItems) {
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
        ClassItem classItem=mClassItems.get(position);
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_driving,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        TextView tv_classno= (TextView) view.findViewById(R.id.tv_classno);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);
        TextView tv_select= (TextView) view.findViewById(R.id.tv_sel);
        tv_name.setText(classItem.getClassName());
        tv_price.setText("￥"+classItem.getClassPrice());
        tv_classno.setText(classItem.getClassNo());
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("data",position+"");
                mOnSelectListener.onSelect(position);
               // mContext.startActivity(new Intent(mContext, DrivingOrderActivity.class));
            }
        });
        return view;
    }
}
