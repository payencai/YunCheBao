package com.cheyibao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tool.view.betterDoubleGrid.holder.ItemViewHolder;

import java.util.List;

/**
 * auther: baiiu
 * time: 16/6/5 05 23:28
 * description:
 */
public class PriceGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<String> topGridData;
    private View.OnClickListener mListener;

    public PriceGridAdapter(Context context, List<String> topGridData, View.OnClickListener listener) {
        this.mContext = context;
        this.topGridData = topGridData;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new ItemViewHolder(mContext, parent, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.bind(topGridData.get(position));
    }

    @Override
    public int getItemCount() {
        return topGridData.size();
    }
}
