package com.xihubao.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sdhcjhss on 2017/12/7.
 */

public class GoodsListAdapter extends BaseAdapter {
    private Context ctx;
    private List<PhoneShopEntity> list;

    public GoodsListAdapter(Context ctx, List<PhoneShopEntity> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 10;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.good_list_item,null);
            vh.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
            vh.item1 = (TextView) convertView.findViewById(R.id.item1);
            vh.item2 = (TextView) convertView.findViewById(R.id.item2);
            vh.item3 = (TextView) convertView.findViewById(R.id.item3);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        SpannableString sStr = new SpannableString("￥233");
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍   ,0.5表示一半
        sStr.setSpan(new RelativeSizeSpan(1.8f), 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        vh.item2.setText(sStr);
//        vh.item2.setCharSequence(R.id.text11, "setText", WordtoSpan);
//        ComponentName com = new ComponentName("com.jftt.widget", "com.jftt.widget.MyWidgetProvider");
//        appWidgetManager.updateAppWidget(com, remoteViews);
//        CharSequence charSequence;
//        String content = "￥<font textSize =16>233</font>";
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            charSequence = Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            charSequence = Html.fromHtml(content);
//        }
//        vh.item2.setText(charSequence);
        return convertView;
    }

    public class ViewHolder{
        SimpleDraweeView img;
        public TextView item1;
        public TextView item2;
        public TextView item3;
    }
}
