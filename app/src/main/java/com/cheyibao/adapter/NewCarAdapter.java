package com.cheyibao.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xihubao.model.CarBrand;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/5 18:05
 * 邮箱：771548229@qq..com
 */
public class NewCarAdapter extends BaseAdapter {
    List<CarBrand> mCarBrands;
    private Context mContext;

    public NewCarAdapter( Context context,List<CarBrand> carBrands) {
        mCarBrands = carBrands;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCarBrands.size();
    }

    @Override
    public Object getItem(int position) {
        return mCarBrands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_car, parent, false);
        CarBrand car = mCarBrands.get(position);
        TextView tv_name= (TextView) view.findViewById(R.id.item_tv);
        SimpleDraweeView simpleDraweeView= (SimpleDraweeView) view.findViewById(R.id.item_iv);
        tv_name.setText(car.getName());
        if(!TextUtils.isEmpty(car.getImage())){
            Uri uri = Uri.parse(car.getImage());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            simpleDraweeView.setController(controller);
        }

        return view;
    }
}
