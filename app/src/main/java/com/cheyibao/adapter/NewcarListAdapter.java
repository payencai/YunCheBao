package com.cheyibao.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.caryibao.NewCar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/10 12:19
 * 邮箱：771548229@qq..com
 */
public class NewcarListAdapter extends BaseQuickAdapter<NewCar, BaseViewHolder> {
    public NewcarListAdapter( @Nullable List<NewCar> data) {
        super(R.layout.car_list_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewCar item) {
        SimpleDraweeView img = (SimpleDraweeView)helper.getView(R.id.img);
        TextView name= (TextView)helper.getView(R.id.name);
        TextView name2= (TextView)helper.getView(R.id.name2);
        TextView tv_price= (TextView)helper.getView(R.id.item);
        String value=item.getSecondName();
        if(!"null".equals(item.getThirdName()))
            value=value+item.getThirdName();
        name2.setText(value);
        String imgs=item.getCarCategoryDetail().getBanner1();
        if(!TextUtils.isEmpty(imgs)&&imgs.contains(","))
            imgs=imgs.split(",")[0];
        img.setImageURI(Uri.parse(imgs));
        name.setText(item.getFirstName());
        if(item.getMinPrice()>10000)
          tv_price.setText("￥"+item.getMinPrice()/10000+"万");
        else{
            tv_price.setText("￥"+item.getMinPrice());
        }
    }
}
