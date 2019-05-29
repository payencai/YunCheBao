package com.vipcenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vipcenter.model.GoodsCollect;
import com.vipcenter.model.NewCarCollect;
import com.vipcenter.model.OldCarCollect;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 全部收藏商品列表适配器
 */
public class OldcarCollectAdapter extends BaseQuickAdapter<OldCarCollect,BaseViewHolder> {
    public OldcarCollectAdapter(int layoutResId, @Nullable List<OldCarCollect> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OldCarCollect item) {
        TextView tv_name = (TextView) helper.getView(R.id.name);
        TextView tv_price = (TextView) helper.getView(R.id.item);
        SimpleDraweeView img = (SimpleDraweeView) helper.getView(R.id.img);
        String imgs=item.getCarImage();
        if(!TextUtils.isEmpty(imgs)){
            if(imgs.contains(",")){
                imgs=imgs.split(",")[0];
            }
        }else{
            img.setImageResource(R.mipmap.icon);
        }
        img.setImageURI(Uri.parse(imgs));
        if(item.getCarPrice()>10000)
            tv_price.setText(item.getCarPrice()/10000+"万");
        else{
            tv_price.setText(item.getCarPrice()+"元");
        }
        tv_name.setText(item.getFirstName()+item.getSecondName()+item.getThirdName());
    }

}
