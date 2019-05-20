package com.cheyibao.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheyibao.model.DrvingSchool;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 17:23
 * 邮箱：771548229@qq..com
 */
public class StudyItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<DrvingSchool> mDrvingSchools;

    public StudyItemAdapter(Context context, List<DrvingSchool> drvingSchools) {
        mContext = context;
        mDrvingSchools = drvingSchools;
    }

    @Override
    public int getCount() {
        return mDrvingSchools.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrvingSchools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrvingSchool drvingSchool = mDrvingSchools.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.driving_school_list_item_layout, null);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
        TextView dis = (TextView) convertView.findViewById(R.id.dis);
        TextView tv_score = (TextView) convertView.findViewById(R.id.score);
        TextView address = (TextView) convertView.findViewById(R.id.address);
        SimpleRatingBar simpleRatingBar = (SimpleRatingBar) convertView.findViewById(R.id.starbar);
        simpleRatingBar.setRating((float) drvingSchool.getScore());
        SimpleDraweeView draweeView = (SimpleDraweeView) convertView.findViewById(R.id.img);
        name.setText(drvingSchool.getName());
        DecimalFormat df = new DecimalFormat("0.00");
        tv_grade.setText(drvingSchool.getGrade()+"");
        dis.setText("距离" + df.format(drvingSchool.getDistance()) + "km");
        int score=(int)drvingSchool.getScore();
        tv_score.setText(score+"");
        address.setText(drvingSchool.getAddress());
        if (!TextUtils.isEmpty(drvingSchool.getLogo()))
            draweeView.setImageURI(Uri.parse(drvingSchool.getLogo()));
        return convertView;
    }
}
