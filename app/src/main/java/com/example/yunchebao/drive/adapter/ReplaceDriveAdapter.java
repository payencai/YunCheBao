package com.example.yunchebao.drive.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.drive.model.ReplaceDrive;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/11 18:13
 * 邮箱：771548229@qq..com
 */
public class ReplaceDriveAdapter extends BaseQuickAdapter<ReplaceDrive,BaseViewHolder> {
    public ReplaceDriveAdapter(int layoutResId, @Nullable List<ReplaceDrive> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReplaceDrive item) {
        ImageView iv_img=helper.getView(R.id.iv_img);

        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_grade=helper.getView(R.id.tv_grade);
        TextView tv_dis=helper.getView(R.id.tv_dis);
        TextView tv_address=helper.getView(R.id.tv_address);
        SimpleRatingBar simpleRatingBar=helper.getView(R.id.starbar);
        simpleRatingBar.setRating(item.getScore());
        Glide.with(mContext).load(item.getLogo()).into(iv_img);
        tv_name.setText(item.getShopName());
        tv_grade.setText(""+item.getGrade());
        tv_address.setText(item.getAddress());
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        tv_dis.setText("距离"+df.format(item.getDistance())+"km");
    }
    public static String doubleTranString(double num)
    {
        if(num % 1.0 == 0)
        {
            return String.valueOf((long)num);
        }

        return String.valueOf(num);
    }
}
