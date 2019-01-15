package com.cheyibao.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.model.NewCarParams;
import com.example.yunchebao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xihubao.model.CarBrand;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/5 18:05
 * 邮箱：771548229@qq..com
 */
public class NewCarParamsAdapter extends BaseAdapter {
    List<NewCarParams.ParamBean> mCarBrands;
    private Context mContext;

    public NewCarParamsAdapter(Context context, List<NewCarParams.ParamBean> carBrands) {
        mCarBrands = carBrands;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCarBrands.size();
    }

    @Override
    public Object getItem(int position) {
        return mCarBrands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newcar_param, parent, false);
        LinearLayout ll_top= (LinearLayout) convertView.findViewById(R.id.ll_top);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_param= (TextView) convertView.findViewById(R.id.tv_param);
        TextView tv_value= (TextView) convertView.findViewById(R.id.tv_value);
        tv_name.setText(mCarBrands.get(position).getParamName());
        tv_value.setText(mCarBrands.get(position).getParamValue());
        if(!TextUtils.isEmpty(mCarBrands.get(position).getParent())){
            ll_top.setVisibility(View.VISIBLE);
            tv_param.setText(mCarBrands.get(position).getParent());
        }
        return convertView;
    }
}
