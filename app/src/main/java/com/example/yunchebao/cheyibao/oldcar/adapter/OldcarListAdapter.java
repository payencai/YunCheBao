package com.example.yunchebao.cheyibao.oldcar.adapter;

import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.adapter.CarRecommendListAdapter;
import com.cheyibao.model.OldCar;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tool.MathUtil;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/22 11:24
 * 邮箱：771548229@qq..com
 */
public class OldcarListAdapter extends BaseQuickAdapter<OldCar, BaseViewHolder> {
    public OldcarListAdapter(int layoutResId, @Nullable List<OldCar> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OldCar oldCar) {
        SimpleDraweeView img = (SimpleDraweeView) helper.getView(R.id.img);
        TextView item2 = (TextView) helper.getView(R.id.item2);
        TextView name = (TextView) helper.getView(R.id.item1);
        TextView tv_name2 = (TextView) helper.getView(R.id.tv_name2);
        TextView tv_price = (TextView) helper.getView(R.id.tv_price);
        TextView tv_newprice = (TextView) helper.getView(R.id.tv_newprice);

        if (!TextUtils.isEmpty(oldCar.getCarImage())) {
            if(oldCar.getCarImage().contains(",")){
                img.setImageURI(Uri.parse(oldCar.getCarImage().split(",")[0]));
            }else{
                img.setImageURI(Uri.parse(oldCar.getCarImage()));
            }

        }
        name.setText(oldCar.getFirstName());
        tv_name2.setText(oldCar.getSecondName() + oldCar.getThirdName());

        String value = oldCar.getRegistrationTime().substring(0, 4) + "/" + oldCar.getDistance() + "  " + oldCar.getRegistrationAddress();
        if (oldCar.getType() == 1) {
            value = value + "  商家";
        } else {
            value = value + "  个人";
        }
        item2.setText(value);
        String oldprice = MathUtil.getDoubleTwo(oldCar.getOldPrice());
        String newprice = MathUtil.getDoubleTwo(oldCar.getNewPrice());
        if (oldCar.getOldPrice() > 10000) {
            oldprice = MathUtil.getDoubleTwo(oldCar.getOldPrice() / 10000) + "万";
        }
        if (oldCar.getNewPrice() > 10000) {
            newprice = MathUtil.getDoubleTwo(oldCar.getNewPrice() / 10000) + "万";
        }
        tv_price.setText("低至￥" + oldprice);
        tv_newprice.setText("新车含税￥" + newprice);
        tv_newprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
