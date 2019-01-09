package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tool.StatusTools;
import com.tool.view.HorizontalListView;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by pengying on 2017/3/25.
 */
public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private int typeId;
    private Object obj;
    private List<PhoneOrderEntity> list;

    public OrderListAdapter(Context context, int typeId, Object obj, List<PhoneOrderEntity> list) {
        this.context = context;
        this.typeId = typeId;
        this.obj = obj;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 5;
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
        PhoneOrderEntity entity = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_all, null);
            vh.shopName = (TextView) convertView.findViewById(R.id.shopName);
            vh.hl_good= (HorizontalListView) convertView.findViewById(R.id.hl_good);
            vh.orderStatus = (TextView) convertView.findViewById(R.id.statusName);
            vh.totalNum = (TextView) convertView.findViewById(R.id.item1);
            vh.totalPrice = (TextView) convertView.findViewById(R.id.item2);
            vh.quxiao = (SuperTextView) convertView.findViewById(R.id.quxiao);
            vh.shouhuo = (SuperTextView) convertView.findViewById(R.id.shouhuo);
            vh.shouhou = (SuperTextView) convertView.findViewById(R.id.shouhou);
            vh.pingjia = (SuperTextView) convertView.findViewById(R.id.pingjia);
            vh.lianxi = (SuperTextView) convertView.findViewById(R.id.lianxi);
            vh.fukuan = (SuperTextView) convertView.findViewById(R.id.fukuan);
            vh.tixing = (SuperTextView) convertView.findViewById(R.id.tixing);
            vh.yanchang = (SuperTextView) convertView.findViewById(R.id.yanchang);
            vh.wuliu = (SuperTextView) convertView.findViewById(R.id.wuliu);
            vh.zailai = (SuperTextView) convertView.findViewById(R.id.zailai);



//            switch (getItemViewType(position)) {
//                case 1: // head
//                    convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_order_list_head, null);
//                    vh.shopName = (TextView) convertView.findViewById(R.id.shopName);
//                    vh.orderStatus = (TextView) convertView.findViewById(R.id.statusName);
//                    break;
//                case 2:// content
//                    convertView = View.inflate(context, R.layout.item_shop_order_list_content, null);
//                    vh.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
//                    vh.des = (TextView) convertView.findViewById(R.id.des);
//                    vh.name = (TextView) convertView.findViewById(R.id.name);
//                    vh.price = (TextView) convertView.findViewById(R.id.price);
//                    vh.num = (TextView) convertView.findViewById(R.id.num);
//                    vh.orderLay = (LinearLayout) convertView.findViewById(R.id.orderLay);
//                    break;
//                case 3: // bottom
//                    convertView = View.inflate(context, R.layout.item_shop_order_list_bottom, null);
//                    vh.totalNum = (TextView) convertView.findViewById(R.id.item1);
//                    vh.totalPrice = (TextView) convertView.findViewById(R.id.item2);
//                    vh.quxiao = (SuperTextView) convertView.findViewById(R.id.quxiao);
//                    vh.shouhuo = (SuperTextView) convertView.findViewById(R.id.shouhuo);
//                    vh.shouhou = (SuperTextView) convertView.findViewById(R.id.shouhou);
//                    vh.pingjia = (SuperTextView) convertView.findViewById(R.id.pingjia);
//                    vh.lianxi = (SuperTextView) convertView.findViewById(R.id.lianxi);
//                    vh.fukuan = (SuperTextView) convertView.findViewById(R.id.fukuan);
//                    vh.tixing = (SuperTextView) convertView.findViewById(R.id.tixing);
//                    vh.yanchang = (SuperTextView) convertView.findViewById(R.id.yanchang);
//                    vh.wuliu = (SuperTextView) convertView.findViewById(R.id.wuliu);
//                    vh.zailai = (SuperTextView) convertView.findViewById(R.id.zailai);
//                    break;
//            }
            convertView.setTag(vh);
        } else {
            vh = (viewHolder) convertView.getTag();
        }
        vh.shopName.setText("云南鸿通");
        vh.orderStatus.setText(StatusTools.getOrderStatusName(entity.getStatus()));
//        vh.orderLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                reflectMethod(0, position);
//            }
//        });
       // vh.hl_good.se
        vh.lianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(1, position);
            }
        });
        vh.quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(2, position);
            }
        });
        vh.fukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(3, position);
            }
        });
        vh.shouhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(4, position);
            }
        });
        vh.tixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(5, position);
            }
        });
        vh.yanchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(6, position);
            }
        });
        vh.wuliu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(7, position);
            }
        });
        vh.shouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(8, position);
            }
        });
        vh.zailai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(9, position);
            }
        });
        vh.pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(10, position);
            }
        });
        if (entity.getStatus().equals("0")) {//全部
//                    vh.orderStatus.setText("交易完成");
        } else if (entity.getStatus().equals("1")) {
//                    vh.orderStatus.setText("待付款");
            vh.quxiao.setVisibility(View.VISIBLE);
            vh.shouhuo.setVisibility(View.GONE);
            vh.shouhou.setVisibility(View.GONE);
            vh.pingjia.setVisibility(View.GONE);
            vh.lianxi.setVisibility(View.VISIBLE);
            vh.fukuan.setVisibility(View.VISIBLE);
            vh.tixing.setVisibility(View.GONE);
            vh.yanchang.setVisibility(View.GONE);
            vh.wuliu.setVisibility(View.GONE);
            vh.zailai.setVisibility(View.GONE);
        } else if (entity.getStatus().equals("2")) {
//                    vh.orderStatus.setText("待发货");
            vh.quxiao.setVisibility(View.GONE);
            vh.shouhuo.setVisibility(View.GONE);
            vh.shouhou.setVisibility(View.VISIBLE);
            vh.pingjia.setVisibility(View.GONE);
            vh.lianxi.setVisibility(View.VISIBLE);
            vh.fukuan.setVisibility(View.GONE);
            vh.tixing.setVisibility(View.VISIBLE);
            vh.yanchang.setVisibility(View.GONE);
            vh.wuliu.setVisibility(View.GONE);
            vh.zailai.setVisibility(View.GONE);
        } else if (entity.getStatus().equals("3")) {
//                    vh.orderStatus.setText("待收货");
            vh.quxiao.setVisibility(View.GONE);
            vh.shouhuo.setVisibility(View.VISIBLE);
            vh.shouhou.setVisibility(View.GONE);
            vh.pingjia.setVisibility(View.GONE);
            vh.lianxi.setVisibility(View.GONE);
            vh.fukuan.setVisibility(View.GONE);
            vh.tixing.setVisibility(View.GONE);
            vh.yanchang.setVisibility(View.VISIBLE);
            vh.wuliu.setVisibility(View.VISIBLE);
            vh.zailai.setVisibility(View.GONE);
        } else if (entity.getStatus().equals("4")) {
//                    vh.orderStatus.setText("待评价");
            vh.quxiao.setVisibility(View.GONE);
            vh.shouhuo.setVisibility(View.GONE);
            vh.shouhou.setVisibility(View.GONE);
            vh.pingjia.setVisibility(View.VISIBLE);
            vh.lianxi.setVisibility(View.GONE);
            vh.fukuan.setVisibility(View.GONE);
            vh.tixing.setVisibility(View.GONE);
            vh.yanchang.setVisibility(View.GONE);
            vh.wuliu.setVisibility(View.VISIBLE);
            vh.zailai.setVisibility(View.VISIBLE);
        }
//
        return convertView;
    }

    public class viewHolder {
        HorizontalListView hl_good;
        public TextView shopName;
        public TextView orderStatus;
        public SuperTextView lianxi;
        public SuperTextView quxiao;
        public SuperTextView fukuan;
        public SuperTextView shouhou;
        public SuperTextView tixing;
        public SuperTextView yanchang;
        public SuperTextView wuliu;
        public SuperTextView shouhuo;
        public SuperTextView zailai;
        public SuperTextView pingjia;
        public TextView price;
        public TextView name;
        public TextView totalNum;
        public TextView totalPrice;
        public TextView des;
        public TextView num;
        public SimpleDraweeView img;
        public LinearLayout orderLay;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
}
