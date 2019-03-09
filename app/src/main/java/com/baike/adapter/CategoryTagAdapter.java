package com.baike.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baike.model.ClassifyWiki;
import com.example.yunchebao.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;


import java.util.List;

/**
 * 作者：凌涛 on 2019/1/22 18:40
 * 邮箱：771548229@qq..com
 */
public class CategoryTagAdapter extends TagAdapter<ClassifyWiki> {

    public CategoryTagAdapter(List<ClassifyWiki> datas) {
        super(datas);
    }

    @Override
    public boolean setSelected(int position, ClassifyWiki classifyWiki) {
        return super.setSelected(position, classifyWiki);
    }

    @Override
    public void onSelected(int position, View view) {
        TextView tv_tag= (TextView) view;
        tv_tag.setTextColor(view.getContext().getResources().getColor(R.color.blue_9DD9));
        tv_tag.setBackgroundResource(R.color.white);
        super.onSelected(position, tv_tag);
    }

    @Override
    public void unSelected(int position, View view) {
        TextView tv_tag= (TextView) view;
        tv_tag.setTextColor(view.getContext().getResources().getColor(R.color.gray_66));
        tv_tag.setBackgroundResource(R.color.white);
        super.unSelected(position, tv_tag);

    }

    @Override
    public View getView(FlowLayout parent, int position, ClassifyWiki categoryTag) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag,null);
        TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
        tv_tag.setText(categoryTag.getName());
        return tv_tag;
    }
}
