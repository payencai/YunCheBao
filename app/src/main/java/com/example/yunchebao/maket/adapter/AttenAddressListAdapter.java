package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.vipcenter.model.PersonAddress;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class AttenAddressListAdapter extends BaseAdapter {
    private Context ctx;
    private List<PersonAddress> list;

    public AttenAddressListAdapter(Context ctx, List<PersonAddress> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.text_item_layout, null);
            vh.text = (TextView) convertView.findViewById(R.id.text);
            vh.isDefult = (TextView) convertView.findViewById(R.id.tv_default);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        PersonAddress personAddress=list.get(position);
        if(personAddress.getIsDefault()==1){
            vh.isDefult.setVisibility(View.VISIBLE);
        }
        vh.text.setText(personAddress.getProvince()+personAddress.getCity()+personAddress.getDistrict()+personAddress.getAddress());
        return convertView;
    }

    public class ViewHolder {
        public TextView text;
        public TextView isDefult;
    }
}
