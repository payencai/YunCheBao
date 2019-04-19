package com.cheyibao.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.payencai.library.adapter.BaseAdapter;

public class CarModelsFirstAndSubClassAdapter<T> extends BaseAdapter<T, CarModelsFirstAndSubClassAdapter.ViewHolder> {


    @Override
    public int getLayoutRes(int index) {
        return 0;
    }

    @Override
    public void convert(com.payencai.library.adapter.BaseViewHolder holder, T data, int index) {

    }

    @Override
    public void bind(com.payencai.library.adapter.BaseViewHolder holder, int layoutRes) {

    }

    static class ViewHolder extends BaseViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }
}
