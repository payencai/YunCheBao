package com.system.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.DrvingSchool;
import com.cheyibao.model.NewCar;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.system.model.SearchSchool;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class SearchSchoolAdapter extends BaseQuickAdapter<DrvingSchool,BaseViewHolder> {
    public SearchSchoolAdapter(int layoutResId, @Nullable List<DrvingSchool> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DrvingSchool item) {
        SimpleRatingBar simpleRatingBar=helper.getView(R.id.sr_score);
        ImageView iv_logo=helper.getView(R.id.img);
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_grade=helper.getView(R.id.tv_grade);
        TextView tv_address=helper.getView(R.id.tv_address);
        simpleRatingBar.setRating((float) item.getScore());
        tv_name.setText(item.getName());
        tv_grade.setText(item.getGrade()+"");
        tv_address.setText(item.getProvince()+item.getCity()+item.getDistrict()+item.getAddress());
    }
}
