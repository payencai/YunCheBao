package com.system.adapter;

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
import com.cheyibao.model.DrvingSchool;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.vipcenter.model.SchoolCollect;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 17:23
 * 邮箱：771548229@qq..com
 */
public class SchoolCollectAdapter extends BaseQuickAdapter<SchoolCollect,BaseViewHolder> {


    public SchoolCollectAdapter(int layoutResId, @Nullable List<SchoolCollect> data) {
        super(layoutResId, data);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        SchoolCollect drvingSchool = mDrvingSchools.get(position);
//        convertView = LayoutInflater.from(mContext).inflate(R.layout.driving_school_list_item_layout, null);
//
//        return convertView;
//    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolCollect item) {
        TextView name = (TextView)helper.getView(R.id.name);
        TextView dis = (TextView) helper.getView(R.id.dis);
        dis.setVisibility(View.GONE);
        TextView score = (TextView) helper.getView(R.id.score);
        TextView address = (TextView) helper.getView(R.id.address);
        SimpleRatingBar simpleRatingBar = (SimpleRatingBar) helper.getView(R.id.starbar);
        simpleRatingBar.setRating((float) item.getScore());
        SimpleDraweeView draweeView = (SimpleDraweeView)helper.getView(R.id.img);
        name.setText(item.getName());
        DecimalFormat df = new DecimalFormat("0.00");
        //  dis.setText("距离" + df.format(drvingSchool.get()) + "km");
        score.setText(item.getScore() + "");
        address.setText(item.getAddress());
        if (!TextUtils.isEmpty(item.getLogo()))
            draweeView.setImageURI(Uri.parse(item.getLogo()));
    }
}
