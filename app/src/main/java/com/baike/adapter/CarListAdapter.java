package com.baike.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caryibao.NewCar;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 车列表适配器
 */
public class CarListAdapter extends BaseAdapter {
    private List<NewCar> list;
    private Context ctx;

    public CarListAdapter(Context ctx, List<NewCar> list) {
        this.list = list;
        this.ctx = ctx;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.car_list_item_layout, null);
            vh.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
            vh.name= (TextView) convertView.findViewById(R.id.name);
            vh.name2= (TextView) convertView.findViewById(R.id.name2);
            vh.tv_price= (TextView) convertView.findViewById(R.id.item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        String value=list.get(position).getSecondName();
        if(!"null".equals(list.get(position).getThirdName()))
            value=value+list.get(position).getThirdName();
        vh.name2.setText(value);
        vh.img.setImageURI(Uri.parse(list.get(position).getCarCategoryDetail().getBanner1()));
        vh.name.setText(list.get(position).getFirstName());
        vh.tv_price.setText("￥"+list.get(position).getMinPrice()/10000+"万");
//        }
        return convertView;
    }


    public class ViewHolder {
        public SimpleDraweeView img;
        public TextView name;
        public TextView name2;
        public TextView tv_price;
    }

}
