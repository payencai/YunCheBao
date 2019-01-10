package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.entity.PhoneAddressEntity;
import com.example.yunchebao.R;
import com.vipcenter.model.PersonAddress;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 收货地址列表适配器
 */
public class AddressListAdapter extends BaseAdapter {
    private List<PersonAddress> list;
    private Context ctx;
    private Object obj;

    public AddressListAdapter(Context ctx , List<PersonAddress> list, Object obj) {
        this.list = list;
        this.ctx = ctx;
        this.obj = obj;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.address_list_item_layout,null);
            vh.name = (TextView) convertView.findViewById(R.id.name);
            vh.phone = (TextView) convertView.findViewById(R.id.phone);
            vh.address = (TextView) convertView.findViewById(R.id.address);
            vh.deAddress = (CheckBox) convertView.findViewById(R.id.de_box);
//            vh.deAddress.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    CheckBox v = (CheckBox)view;
//                    if(!v.isChecked()){
//                        v.setChecked(true);
//                        return;
//                    }else {
//                        reflectMethod(2,position);
//                    }
//                }
//            });
            convertView.findViewById(R.id.editLay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reflectMethod(0,position);
                }
            });
            convertView.findViewById(R.id.deleteLay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reflectMethod(1,position);
                }
            });
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        PersonAddress entity = list.get(position);
        vh.name.setText(entity.getNickname());
        vh.phone.setText(entity.getTelephone());
        vh.address.setText(entity.getProvince()+entity.getCity()+entity.getDistrict()+entity.getAddress());
        vh.deAddress.setChecked(entity.getIsDefault()==1);
        vh.deAddress.setClickable(false);
        return convertView;
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

    public class ViewHolder{
        public TextView name;
        public TextView phone;
        public TextView address;
        public CheckBox deAddress;
    }
}
