package com.maket.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.maket.model.GoodsComment;
import com.tool.view.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodsCommentAdapter extends BaseAdapter {
    private Context ctx;
    private List<GoodsComment> list;
    GoodsCommentImageAdapter mGoodsCommentImageAdapter;
    List<String> imageList;

    public GoodsCommentAdapter(Context ctx, List<GoodsComment> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        imageList = new ArrayList<>();
        GoodsComment goodsComment = list.get(position);
        convertView = LayoutInflater.from(ctx).inflate(R.layout.good_comment_list_item, null);
        SimpleDraweeView sdv_head = (SimpleDraweeView) convertView.findViewById(R.id.sd_head);
        TextView tv_name = (TextView) convertView.findViewById(R.id.name);
        TextView item_time = (TextView) convertView.findViewById(R.id.item_time);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        SimpleRatingBar srb_score = (SimpleRatingBar) convertView.findViewById(R.id.srb_score);
        if (!TextUtils.isEmpty(goodsComment.getHeadPortrait())) {
            sdv_head.setImageURI(Uri.parse(goodsComment.getHeadPortrait()));
        }
        tv_name.setText(goodsComment.getNickName());
        item_time.setText(goodsComment.getCreateTime().substring(0, 10));
        content.setText(goodsComment.getContent());
        srb_score.setRating(goodsComment.getScore());
        if (goodsComment.getImgs().contains(",")) {
            String img[] = goodsComment.getImgs().split(",");
            for (int i = 0; i < img.length; i++) {
                imageList.add(img[i]);
            }
        }
        mGoodsCommentImageAdapter = new GoodsCommentImageAdapter(ctx, imageList);
        GridViewForScrollView gv_image = (GridViewForScrollView) convertView.findViewById(R.id.imgList);
        gv_image.setAdapter(mGoodsCommentImageAdapter);
        return convertView;
    }


}
