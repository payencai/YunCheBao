package com.cheyibao.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.CarModelsFirstLevel;
import com.example.yunchebao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarModelsFirstAndSubClassAdapter extends BaseQuickAdapter<CarModelsFirstLevel, CarModelsFirstAndSubClassAdapter.ViewHolder> {


    public CarModelsFirstAndSubClassAdapter(@Nullable List<CarModelsFirstLevel> data) {
        super(R.layout.item_rec_car, data);
    }

    @Override
    protected void convert(ViewHolder helper, CarModelsFirstLevel item) {
        helper.itemTv.setText(item.getName());
        if (!TextUtils.isEmpty(item.getImage())) {
            Uri uri = Uri.parse(item.getImage());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            helper.itemIv.setController(controller);
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.item_iv)
        SimpleDraweeView itemIv;
        @BindView(R.id.item_tv)
        TextView itemTv;
        @BindView(R.id.item_content)
        LinearLayout itemContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
