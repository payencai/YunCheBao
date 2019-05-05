package com.example.yunchebao.road.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.road.model.RoadService;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/29 17:38
 * 邮箱：771548229@qq..com
 */
public class RoadServiceAdapter extends BaseQuickAdapter<RoadService, BaseViewHolder> {
    public RoadServiceAdapter(@Nullable List<RoadService> data) {
        super(R.layout.item_road_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoadService item) {
        TextView title= (TextView) helper.getView(R.id.title);
        TextView content= (TextView) helper.getView(R.id.content);
        title.setText(item.getTitle());
        content.setText(item.getContent());
    }
}
