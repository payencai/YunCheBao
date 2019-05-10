package com.example.yunchebao.yuedan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 约单消息列表适配器
 */
public class BookMessageListAdapter extends BaseAdapter {
    private List<PhoneGoodEntity> list;
    private Context ctx;

    public BookMessageListAdapter(Context ctx, List<PhoneGoodEntity> list) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 4;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.book_message_list_item, null);
            vh.typeName = (TextView) convertView.findViewById(R.id.typeName);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        switch (position) {
            case 0:
                vh.typeName.setText("洗车店");
                break;
            case 1:
                vh.typeName.setText("修车店");
                break;
            case 2:
                vh.typeName.setText("新车整车");
                break;
            case 3:
                vh.typeName.setText("二手车");
                break;
        }
//        final PhoneGoodEntity entity = list.get(position);
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
        public TextView typeName;
    }

}
