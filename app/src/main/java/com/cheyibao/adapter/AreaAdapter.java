package com.cheyibao.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.Area;
import com.example.yunchebao.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AreaAdapter extends BaseQuickAdapter<Area,AreaAdapter.ViewHolder> {

    private int selectedIndex = 0;

    public AreaAdapter(@Nullable List<Area> data) {
        super(R.layout.item_area, data);
    }


    @Override
    protected void convert(ViewHolder helper, Area item) {
        if (item!=null){
            Context context = helper.itemView.getContext();
            helper.tvArea.setText(item.getName());
            if (item.isSelecting()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    helper.itemView.setBackgroundColor(context.getColor(R.color.white));
                }else {
                    helper.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    helper.itemView.setBackgroundColor(context.getColor(R.color.gray_f8));
                }else{
                    helper.itemView.setBackgroundColor(context.getResources().getColor(R.color.gray_f8));
                }
            }
        }
    }

    public void refreshItem(int position){
        Area last = getItem(selectedIndex);
        if (last!=null){
            last.setSelecting(false);
        }
        Area current = getItem(position);
        if (current!=null){
            current.setSelecting(true);
        }
        selectedIndex = position;
        notifyDataSetChanged();
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_area)
        TextView tvArea;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
