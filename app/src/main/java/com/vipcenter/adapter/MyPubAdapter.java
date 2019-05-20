package com.vipcenter.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.Mypublish;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/20 11:24
 * 邮箱：771548229@qq..com
 */
public class MyPubAdapter extends BaseQuickAdapter<Mypublish, BaseViewHolder> {
    private MyPublishListAdapter.onDeleteClickListener mOnDeleteClickListener;

    public void setOnDeleteClickListener(MyPublishListAdapter.onDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    public interface onDeleteClickListener {
        void onClick(int pos);

        void onItemClick(int pos);
    }

    public MyPubAdapter(int layoutResId, @Nullable List<Mypublish> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Mypublish item) {
        TextView tv_content = (TextView) helper.getView(R.id.item1);
        LinearLayout ll_content = (LinearLayout) helper.getView(R.id.ll_content);
        TextView tv_type = (TextView) helper.getView(R.id.item2);
        Button btnDelete = (Button) helper.getView(R.id.btnDelete);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        tv_content.setText(item.getTitle());
        String time = item.getCreateTime();
        tv_time.setText(time);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteClickListener.onClick(helper.getAdapterPosition());

            }
        });
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteClickListener.onItemClick(helper.getAdapterPosition());
            }
        });
        switch (item.getType()) {
            case 1:
                tv_type.setText("自驾游");
                break;
            case 2:
                tv_type.setText("车友会");
                break;

        }
    }
}
