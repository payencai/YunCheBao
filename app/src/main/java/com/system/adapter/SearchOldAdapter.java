package com.system.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.NewCar;
import com.cheyibao.model.OldCar;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class SearchOldAdapter extends BaseQuickAdapter<OldCar,BaseViewHolder> {
    public SearchOldAdapter(int layoutResId, @Nullable List<OldCar> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OldCar item) {
        ImageView iv_logo=helper.getView(R.id.img);

        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_detail=helper.getView(R.id.tv_detail);
        TextView tv_price=helper.getView(R.id.tv_minprice);
        TextView tv_maxprice=helper.getView(R.id.tv_maxprice);
        String imgs=item.getCarCategoryDetail().getBanner1();
        if(!TextUtils.isEmpty(imgs)){
            String images[]=imgs.split(",");
            Glide.with(helper.itemView.getContext()).load(images[0]).into(iv_logo);
        }
        tv_name.setText(item.getFirstName()+" "+item.getSecondName()+" "+item.getThirdName());
        String value=item.getRegistrationTime().substring(0,4)+"/"+item.getDistance()+"  "+item.getRegistrationAddress();
        if(item.getType()==1){
            value=value+"  商家";
        }else{
            value=value+"  个人";
        }
        tv_detail.setText(value);
        tv_price.setText(""+item.getOldPrice());
        tv_maxprice.setText("新车含税￥"+item.getNewPrice());
        tv_maxprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }
}
