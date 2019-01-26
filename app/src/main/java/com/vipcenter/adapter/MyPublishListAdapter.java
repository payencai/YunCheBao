package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.PhoneArticleEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.vipcenter.model.Mypublish;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 浏览历史列表适配器
 */
public class MyPublishListAdapter extends BaseAdapter {
    private List<Mypublish> list;
    private Context ctx;

    public MyPublishListAdapter(Context ctx, List<Mypublish> list) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.my_publish_list_item, null);
        TextView tv_content= (TextView) convertView.findViewById(R.id.item1);
        TextView tv_type= (TextView) convertView.findViewById(R.id.item2);
        TextView tv_date= (TextView) convertView.findViewById(R.id.tv_date);
        TextView tv_month= (TextView) convertView.findViewById(R.id.tv_month);
        tv_content.setText(list.get(position).getTitle());
        String time=list.get(position).getCreateTime().substring(0,10);
        tv_date.setText(time.substring(8,10));
        tv_month.setText(time.substring(5,7)+"月");
        switch (list.get(position).getType()){
            case 1:
                tv_type.setText("自驾游");
                break;
            case 2:
                tv_type.setText("车友会");
                break;
            case 3:
                tv_type.setText("汽车秀");
                break;
            default:
                tv_type.setText("赛事发布");
                break;
        }
        return convertView;
    }


}
