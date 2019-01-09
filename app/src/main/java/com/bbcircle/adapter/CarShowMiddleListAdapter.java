package com.bbcircle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 汽车秀更多精彩列表适配器
 */
public class CarShowMiddleListAdapter extends BaseAdapter {
    private List<PhoneMagEntity> list;
    private Context ctx;

    public CarShowMiddleListAdapter(Context ctx, List<PhoneMagEntity> list) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.car_show_list_item_layout, null);
            vh.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
            vh.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        final PhoneMagEntity entity = list.get(position);
//        vh.name.setText(entity.getName());
//        if (entity.getPic_2() != null && !entity.getPic_2().equals("")){
//            Uri uri = Uri.parse(PlatformContans.rootUrl + entity.getPic_2());
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setUri(uri)
//                    .setAutoPlayAnimations(true)
//                    .build();
//            vh.img.setController(controller);
//        }
        return convertView;
    }


    public class ViewHolder {
        public SimpleDraweeView img;
        public TextView name;
    }

}
