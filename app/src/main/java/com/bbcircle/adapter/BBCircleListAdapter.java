package com.bbcircle.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbcircle.data.SelfDrive;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 宝贝圈列表适配器
 */
public class BBCircleListAdapter extends BaseQuickAdapter<SelfDrive, BaseViewHolder> {


    public BBCircleListAdapter(int layoutResId, @Nullable List<SelfDrive> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SelfDrive carShow) {

        SimpleDraweeView img2 = (SimpleDraweeView) helper.getView(R.id.img2);
        TextView name1 = (TextView) helper.getView(R.id.bottom1);
        TextView name2 = (TextView) helper.getView(R.id.bottom2);
        TextView tv_title = (TextView) helper.getView(R.id.name2);
        ImageView iv_video = (ImageView) helper.getView(R.id.iv_video);
        name2.setText(carShow.getCommentNum() + "回帖");
        TextView tv_num = (TextView) helper.getView(R.id.tv_num);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        tv_num.setText("已报名："+carShow.getEnterNum()+"人");
        tv_num.setVisibility(View.VISIBLE);
        tv_time.setVisibility(View.VISIBLE);
        tv_time.setText("时间："+carShow.getStartTime().substring(5)+"至"+carShow.getEndTime().substring(5));
        name1.setText(carShow.getName());
        tv_title.setText(carShow.getTitle());
        //iv_video.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(carShow.getImage()))
            img2.setImageURI(Uri.parse(carShow.getImage()));
    }




}
