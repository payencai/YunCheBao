package com.vipcenter.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vipcenter.model.NewCarCollect;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/28 17:56
 * 邮箱：771548229@qq..com
 */
public class NewCarCollectAdapter extends BaseQuickAdapter<NewCarCollect,BaseViewHolder> {
    public NewCarCollectAdapter(int layoutResId, @Nullable List<NewCarCollect> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewCarCollect item) {
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
