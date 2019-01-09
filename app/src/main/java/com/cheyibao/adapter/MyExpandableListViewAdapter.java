package com.cheyibao.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/2.
 */

public class MyExpandableListViewAdapter implements ExpandableListAdapter {
    private Context context;
    private List<PhoneGoodEntity> groupData;
    private List<List<PhoneGoodEntity>> childData;
    private Object obj;

    public MyExpandableListViewAdapter(Context context, List<PhoneGoodEntity> groupData, List<List<PhoneGoodEntity>> childData, Object obj) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
        this.obj = obj;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    //获取分组个数
    @Override
    public int getGroupCount() {
        int ret = 0;
//        if (groupData != null) {
//            ret = groupData.size();
//        }
        return 3;
    }

    //获取groupPosition分组，子列表数量
    @Override
    public int getChildrenCount(int groupPosition) {
        int ret = 0;
//        if (childData != null) {
//            ret = childData.get(groupPosition).size();
//        }
        return 3;
    }

    @Override
    public Object getGroup(int groupPosition) {
//        return groupData.get(groupPosition);
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
//        return childData.get(groupPosition).get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.on_sale_car_list_item_layout, null);
            holder = new GroupViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.arrowImg);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
//        PhoneGoodEntity groupData = this.groupData.get(groupPosition);
        //是否展开
        if (isExpanded) {
            holder.img.setImageResource(R.drawable.common_filter_arrow_up);
        } else {
            holder.img.setImageResource(R.drawable.common_filter_arrow_down);
        }
//        holder.tv_name.setText(groupData.getName());
//        holder.tv_num.setText(groupData.getNum());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.new_car_seller_layout, null);
            holder = new ChildViewHolder();
            holder.carDetaiLay = (RelativeLayout) convertView.findViewById(R.id.carDetaiLay);
            holder.btn1 = (SuperTextView) convertView.findViewById(R.id.btn1);
            holder.btn2 = (SuperTextView) convertView.findViewById(R.id.btn2);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.carDetaiLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(0, childPosition);
            }
        });
        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(1, childPosition);
            }
        });
        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(2, childPosition);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class GroupViewHolder {
        ImageView img;
        TextView tv_name, tv_num;
    }

    class ChildViewHolder {
        RelativeLayout carDetaiLay;
        SuperTextView btn1;
        SuperTextView btn2;
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

}