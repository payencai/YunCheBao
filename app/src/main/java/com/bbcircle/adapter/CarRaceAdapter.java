package com.bbcircle.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bbcircle.data.CarFriend;
import com.bbcircle.data.CarRace;
import com.bbcircle.data.CarShow;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/28 18:48
 * 邮箱：771548229@qq..com
 */
public class CarRaceAdapter extends BaseQuickAdapter<CarRace, BaseViewHolder> {

    public CarRaceAdapter(int layoutResId, @Nullable List<CarRace> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarRace carShow) {
        SimpleDraweeView img2 = (SimpleDraweeView) helper.getView(R.id.img2);
        TextView name1 = (TextView) helper.getView(R.id.bottom1);
        TextView name2 = (TextView) helper.getView(R.id.bottom2);
        TextView tv_title=(TextView) helper.getView(R.id.name2);
        ImageView iv_video=(ImageView) helper.getView(R.id.iv_video);
        name2.setText(carShow.getCommentNum()+"回帖");
        name1.setText(carShow.getName());
        tv_title.setText(carShow.getTitle());
        iv_video.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(carShow.getImage()))
            img2.setImageURI(Uri.parse(carShow.getImage()));
    }
}


