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
import com.facebook.drawee.view.SimpleDraweeView;
import com.example.yunchebao.maket.adapter.GoodsOrderImageAdapter;
import com.tool.view.HorizontalListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengying on 2017/3/25.
 */
public class OrderListAdapter extends BaseAdapter {
    GoodsOrderImageAdapter goodsOrderImageAdapter;
    private Context context;
    private int typeId;
    private Object obj;
    private List<PhoneOrderEntity> list;
    List<String> images=new ArrayList<>();
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
    }//不要动这是


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
            vh.hl_good = (HorizontalListView) convertView.findViewById(R.id.hl_good);
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
            vh.root= (LinearLayout) convertView.findViewById(R.id.root);
            convertView.setTag(vh);
        } else {
            vh = (viewHolder) convertView.getTag();
        }

        //  vh.orderStatus.setText(StatusTools.getOrderStatusName(entity.getStatus()));
        vh.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(0, position);
            }
        });
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

        vh.pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(10, position);
            }
        });
        vh.totalNum.setText("共"+list.get(position).getNumber()+"件商品  需付款：");
        vh.totalPrice.setText("￥"+list.get(position).getTotal());
        vh.shopName.setText(list.get(position).getShopName());
        images.clear();
        for (int i = 0; i <list.get(position).getItemList().size() ; i++) {
            images.add(list.get(position).getItemList().get(i).getCommodityImage());
        }
        goodsOrderImageAdapter=new GoodsOrderImageAdapter(context,images);
        vh.hl_good.setAdapter(goodsOrderImageAdapter);
        if (entity.getState() == 1) {

            vh.orderStatus.setText("待付款");
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
        } else if (entity.getState() == 2) {
            vh.orderStatus.setText("待发货");
            vh.quxiao.setVisibility(View.GONE);
            vh.shouhuo.setVisibility(View.GONE);
            vh.shouhou.setVisibility(View.VISIBLE);
            vh.pingjia.setVisibility(View.GONE);
            vh.lianxi.setVisibility(View.GONE);
            vh.fukuan.setVisibility(View.GONE);
            vh.tixing.setVisibility(View.VISIBLE);
            vh.yanchang.setVisibility(View.GONE);
            vh.wuliu.setVisibility(View.GONE);
            vh.zailai.setVisibility(View.GONE);
        } else if (entity.getState() == 3) {
            vh.orderStatus.setText("待收货");
            vh.quxiao.setVisibility(View.GONE);
            vh.shouhuo.setVisibility(View.VISIBLE);
            vh.shouhou.setVisibility(View.GONE);
            vh.pingjia.setVisibility(View.GONE);
            vh.lianxi.setVisibility(View.GONE);
            vh.fukuan.setVisibility(View.GONE);
            vh.tixing.setVisibility(View.GONE);
            vh.yanchang.setVisibility(View.GONE);
            vh.wuliu.setVisibility(View.VISIBLE);
            vh.zailai.setVisibility(View.GONE);
        } else if (entity.getState() == 4) {
            vh.orderStatus.setText("待评价");
            vh.quxiao.setVisibility(View.GONE);
            vh.shouhuo.setVisibility(View.GONE);
            vh.shouhou.setVisibility(View.GONE);
            vh.pingjia.setVisibility(View.VISIBLE);
            vh.lianxi.setVisibility(View.GONE);
            vh.fukuan.setVisibility(View.GONE);
            vh.tixing.setVisibility(View.GONE);
            vh.yanchang.setVisibility(View.GONE);
            vh.wuliu.setVisibility(View.VISIBLE);
            vh.zailai.setVisibility(View.GONE);
        }
//
        return convertView;
    }

    public class viewHolder {
        LinearLayout root;
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
