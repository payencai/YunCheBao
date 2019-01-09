package com.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.PhoneAddressEntity;
import com.example.yunchebao.R;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class AttenAddressListAdapter extends BaseAdapter {
    private Context ctx;
    private List<PhoneAddressEntity> list;

    public AttenAddressListAdapter(Context ctx, List<PhoneAddressEntity> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.text_item_layout, null);
            vh.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView text;
    }
}
