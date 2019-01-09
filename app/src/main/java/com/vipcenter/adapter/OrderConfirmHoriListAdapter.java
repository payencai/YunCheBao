package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 列表适配器
 */
public class OrderConfirmHoriListAdapter extends BaseAdapter {
    private List<PhoneOrderEntity> list;
    private Context ctx;
    private Object obj;

    public OrderConfirmHoriListAdapter(Context ctx, List<PhoneOrderEntity> list, Object obj) {
        this.list = list;
        this.ctx = ctx;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.order_confirm_hori_item, null);
            vh.btn = (SuperTextView) convertView.findViewById(R.id.btn);
            vh.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reflectMethod(0, position);
                }
            });
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    /**
     * 反射调用委托方法
     *
     * @param tag
     */
    public void reflectMethod(Integer tag, Integer loc) {
        Method cMethod;
        try {
            cMethod = obj.getClass().getMethod("onShortcutMenuClickListener",
                    new Class[]{Class.forName("java.lang.Integer"),
                            Class.forName("java.lang.Integer")});
            cMethod.invoke(obj, tag, loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder {
        public SuperTextView btn;
        public TextView name;
        public TextView phone;
        public TextView address;
    }
}
