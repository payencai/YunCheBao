package com.bbcircle.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bbcircle.data.WashCollect;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sdhcjhss on 2017/12/7.
 */

public class WashCollectAdapter extends BaseQuickAdapter<WashCollect,BaseViewHolder> {


    public WashCollectAdapter(int layoutResId, @Nullable List<WashCollect> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WashCollect item) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView)helper.getView(R.id.img);
        TextView item1 = (TextView) helper.getView(R.id.item1);
        TextView item2 = (TextView) helper.getView(R.id.item2);
        TextView item3 = (TextView) helper.getView(R.id.item3);
        TextView item4 = (TextView) helper.getView(R.id.item4);
        TextView item5 = (TextView) helper.getView(R.id.item5);
        if (item.getLogo() != null) {
            Uri uri = Uri.parse(item.getLogo());
            simpleDraweeView.setImageURI(uri);
        }
        item1.setText(item.getShopName());
        item2.setText("评分" + item.getScore() + "|订单" + item.getOrderNum());
        item3.setText(item.getAddress());
        item4.setVisibility(View.GONE);
        item5.setVisibility(View.GONE);
    }



//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View view = LayoutInflater.from(ctx).inflate(R.layout.wash_list_item, null);
//        WashCollect carShop = list.get(position);
//
//        return view;
//    }


}
