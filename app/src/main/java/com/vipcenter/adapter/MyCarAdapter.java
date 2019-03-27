package com.vipcenter.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.MyCar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/27 10:17
 * 邮箱：771548229@qq..com
 */
public class MyCarAdapter extends BaseQuickAdapter<MyCar, BaseViewHolder> {
    public MyCarAdapter(int layoutResId, @Nullable List<MyCar> data) {
        super(layoutResId, data);
    }

    public interface onChooseClickListener {
        void choose(ImageView iv_choose);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCar item) {
        helper.addOnClickListener(R.id.ll_check)
                .addOnClickListener(R.id.ll_item).addOnClickListener(R.id.btnDelete);
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        ImageView iv_choose = helper.getView(R.id.iv_choose);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_state = helper.getView(R.id.tv_state);
        LinearLayout ll_check=helper.getView(R.id.ll_check);
        Glide.with(helper.itemView.getContext()).load(item.getCarLogo()).into(iv_logo);
        if (item.getIsDefault() == 1) {
            iv_choose.setImageResource(R.mipmap.select);
        }else{
            iv_choose.setImageResource(R.mipmap.unselect);
        }
        tv_name.setText(item.getModels());
        ll_check.setVisibility(View.GONE);
        switch (item.getType()){
            case 1:
                tv_state.setText("认证中");
                break;
            case 2:
                tv_state.setText("认证成功");
                ll_check.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_state.setText("认证失败");
                break;
        }
    }
}
