package com.baike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 车列表适配器
 */
public class SellerListAdapter extends BaseAdapter {
    private List<PhoneGoodEntity> list;
    private Context ctx;
    private Object obj;

    public SellerListAdapter(Context ctx, List<PhoneGoodEntity> list, Object obj) {
        this.list = list;
        this.ctx = ctx;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        return 7;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.new_car_seller_layout, null);
            vh.btn1 = (SuperTextView) convertView.findViewById(R.id.btn1);
            vh.btn2 = (SuperTextView) convertView.findViewById(R.id.btn2);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        final PhoneGoodEntity entity = list.get(position);
//        vh.name.setText(entity.getName());
//        if (entity.getPic_2() != null && !entity.getPic_2().equals("")){
//            Uri uri = Uri.parse(PlatformContans.rootUrl + entity.getPic_2());
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setUri(uri)
//                    .setAutoPlayAnimations(true)
//                    .build();
//            vh.img.setController(controller);
//        }
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

    public class ViewHolder {
        public TextView name;
        public SuperTextView btn1;
        public SuperTextView btn2;
    }

}
