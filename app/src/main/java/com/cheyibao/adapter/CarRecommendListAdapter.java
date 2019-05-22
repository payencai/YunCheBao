package com.cheyibao.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheyibao.model.OldCar;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tool.MathUtil;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 二手车好车推荐列表适配器
 */
public class CarRecommendListAdapter extends BaseAdapter {
    private List<OldCar> list;
    private Context ctx;

    public CarRecommendListAdapter(Context ctx, List<OldCar> list) {
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
        OldCar oldCar=list.get(position);
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.car_recommend_list_item, null);
            vh.img= (SimpleDraweeView) convertView.findViewById(R.id.img);
            vh.item2 = (TextView) convertView.findViewById(R.id.item2);
            vh.name = (TextView) convertView.findViewById(R.id.item1);
            vh.tv_name2 = (TextView) convertView.findViewById(R.id.tv_name2);
            vh.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            vh.tv_newprice = (TextView) convertView.findViewById(R.id.tv_newprice);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(oldCar.getCarCategoryDetail().getBanner1())){
             vh.img.setImageURI(Uri.parse(oldCar.getCarCategoryDetail().getBanner1()));
        }
        vh.name.setText(oldCar.getFirstName());
        vh.tv_name2.setText(oldCar.getSecondName()+oldCar.getFirstName());

//        if (position == 1) {
//
//            vh.item2.setText("2016年/1.0万公里  淄博  个人");
//        }
        String value=oldCar.getRegistrationTime().substring(0,4)+"/"+oldCar.getDistance()+"  "+oldCar.getRegistrationAddress();
        if(oldCar.getType()==1){
            value=value+"  商家";
        }else{
            value=value+"  个人";
        }
        vh.item2.setText(value);
        String oldprice= MathUtil.getDoubleTwo(oldCar.getOldPrice());
        String newprice= MathUtil.getDoubleTwo(oldCar.getNewPrice());
        if(oldCar.getOldPrice()>10000){
            oldprice= MathUtil.getDoubleTwo(oldCar.getOldPrice()/10000)+"万";
        }
        if(oldCar.getNewPrice()>10000){
            newprice= MathUtil.getDoubleTwo(oldCar.getNewPrice()/10000)+"万";
        }

        vh.tv_price.setText("低至￥"+oldprice);
        vh.tv_newprice.setText("新车含税￥"+newprice);
        vh.tv_newprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertView;
    }


    public class ViewHolder {
        public SimpleDraweeView img;
        public TextView name;
        public TextView item2;
        public TextView tv_name2;
        public TextView tv_price;
        public TextView tv_newprice;
    }

}
