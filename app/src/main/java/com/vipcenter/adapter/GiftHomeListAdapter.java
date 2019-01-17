package com.vipcenter.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vipcenter.model.Gift;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 全部收藏店铺首页适配器
 */
public class GiftHomeListAdapter extends BaseQuickAdapter<Gift,BaseViewHolder> {
    private List<Gift> list;

    public GiftHomeListAdapter(int layoutResId, @Nullable List<Gift> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Gift item) {

        SimpleDraweeView  img = (SimpleDraweeView) helper.getView(R.id.img);
        TextView name= (TextView)helper.getView(R.id.tv_name);
        String[] imgs=item.getCommodityImage().split(",");
        String url="";
        for (int i = 0; i <imgs.length ; i++) {
            if(i==0){
                url=imgs[0];
            }
        }
        if(!TextUtils.isEmpty(url)){
            Uri uri = Uri.parse(url);
            img.setImageURI(uri);
        }else{
            Uri uri = Uri.parse(item.getCommodityImage());
            img.setImageURI(uri);
        }

        img.getLayoutParams().height=item.getHeight();
        name.setText(item.getCommodityName());
    }

}
