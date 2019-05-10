package com.example.yunchebao.yuedan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entity.PhoneCommentEntity;
import com.example.yunchebao.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 约单需求列表适配器
 */
public class BookNeedListAdapter extends BaseAdapter {
    private List<PhoneCommentEntity> list;
    private Context ctx;
    private Object obj;

    public BookNeedListAdapter(Context ctx, List<PhoneCommentEntity> list, Object obj) {
        this.list = list;
        this.ctx = ctx;
        this.obj = obj;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.book_need_list_item, null);
            vh.btn1 = (LinearLayout) convertView.findViewById(R.id.lay1);
            vh.btn2 = (LinearLayout) convertView.findViewById(R.id.lay2);
            vh.typeName = (TextView) convertView.findViewById(R.id.typeName);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
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
        switch (position) {
            case 0:
                vh.typeName.setText("新车整车");
                break;
            case 1:
                vh.typeName.setText("二手车");
                break;
            case 2:
                vh.typeName.setText("洗车店");
                break;
            case 3:
                vh.typeName.setText("修理店");
                break;
        }
        vh.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(position + 1, position);
            }
        });
        vh.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                reflectMethod(2, position);
            }
        });
        return convertView;
    }

    /**
     * 反射调用委托方法
     *
     * @param tag
     */
    public void reflectMethod(Integer tag, Integer loc) {
        Method cMethod;
        try {
            cMethod = obj.getClass().getMethod("onShortcutMenuClickListener",
                    new Class[]{Class.forName("java.lang.Integer"),
                            Class.forName("java.lang.Integer")});
            cMethod.invoke(obj, tag, loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder {
        public TextView name;
        public TextView typeName;
        public LinearLayout btn1;
        public LinearLayout btn2;
    }

}
