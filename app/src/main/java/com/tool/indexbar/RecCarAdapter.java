package com.tool.indexbar;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yunchebao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xihubao.model.CarBrand;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecCarAdapter extends RecyclerView.Adapter<RecCarAdapter.Holder> {

    private List<CarBrand> mList;

    private Activity mActivity;

    public RecCarAdapter(Activity activity) {
        mList = new ArrayList<>();
        mActivity = activity;
    }

    public void addDatas(List<CarBrand> data) {
        this.mList.clear();
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_car, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        CarBrand car = mList.get(position);

        holder.tv_name.setText(car.getName());
        if(!TextUtils.isEmpty(car.getImage())){
            Uri uri = Uri.parse(car.getImage());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            holder.iv_logo.setController(controller);
        }
        holder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });
//        Glide.with(mActivity).load(car.getLogoUrl()).into(holder.iv_logo);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv)
        TextView tv_name;

        @BindView(R.id.item_iv)
        SimpleDraweeView iv_logo;
        @BindView(R.id.item_content)
        LinearLayout iv_content;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
//
//        @OnClick(R.id.item_content)
//        void tvClick() {
////            Toast.makeText(itemView.getContext(), "你点击到了" + tv_name.getText(), Toast.LENGTH_SHORT).show();
//        }
    }

}
