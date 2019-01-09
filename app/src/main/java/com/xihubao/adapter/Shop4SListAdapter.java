package com.xihubao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.FourShop;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by pengying on 2017/3/25.
 */
public class Shop4SListAdapter extends BaseAdapter {

    private Context context;
    private int typeId;
    private Object obj;
    private List<FourShop> list;

    public Shop4SListAdapter(Context context, int typeId, Object obj) {
        this.context = context;
        this.typeId = typeId;
        this.obj = obj;
    }

    public Shop4SListAdapter(Context context, int typeId, Object obj, List<FourShop> list){
        this.context = context;
        this.typeId = typeId;
        this.obj = obj;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder vh = new viewHolder();
//        PhoneShopEntity entity = list.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_4s_list_item_layout,null);
            vh.btn1 = (SuperTextView) convertView.findViewById(R.id.btn1);
            vh.btn2 = (SuperTextView) convertView.findViewById(R.id.btn2);
            convertView.setTag(vh);
        }else{
            vh = (viewHolder) convertView.getTag();
        }
        vh.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(1, position);
            }
        });
        vh.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(2, position);
            }
        });

        return convertView;
    }

    public class viewHolder{
        public TextView item1;
        public SuperTextView btn1;
        public SuperTextView btn2;
    }


    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * 反射调用委托方法
     * @param tag
     */
    public void reflectMethod(Integer tag, Integer loc){
        Method cMethod;
        try
        {
            cMethod =obj.getClass().getMethod("onShortcutMenuClickListener",
                    new Class[]{Class.forName("java.lang.Integer"),
                            Class.forName("java.lang.Integer")});
            cMethod.invoke(obj, tag, loc);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
