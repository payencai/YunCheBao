package com.example.yunchebao.washrepair;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.adapter.FourShopCommentAdapter;
import com.example.yunchebao.fourshop.bean.FourShopComment;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.luffy.imagepreviewlib.core.PictureConfig;
import com.payencai.library.view.CircleImageView;
import com.vipcenter.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/4/19 09:56
 * 邮箱：771548229@qq..com
 */
public class WashrepairCommentAdapter extends BaseQuickAdapter<WashRepairComment, BaseViewHolder> {

    public WashrepairCommentAdapter(int layoutResId, @Nullable List<WashRepairComment> data) {
        super(layoutResId, data);
    }
    public FourShopCommentAdapter.OnImageClick onImageClick;
    public interface  OnImageClick{
        void onClick(int pos,ArrayList<String> images);
    }
    public void setOnImageClick(FourShopCommentAdapter.OnImageClick onImageClick){
        this.onImageClick=onImageClick;
    }
    @Override
    protected void convert(BaseViewHolder helper, WashRepairComment item) {
        ArrayList<String> images; images = new ArrayList<>();
        GridView gv_photo = (GridView) helper.getView(R.id.gv_photo);
        gv_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onImageClick.onClick(position,images);
            }

        });
        TextView iv_content = (TextView) helper.getView(R.id.iv_content);
        CircleImageView userhead = (CircleImageView) helper.getView(R.id.userhead);
        TextView tv_name = (TextView) helper.getView(R.id.tv_name);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        SimpleRatingBar starbar = (SimpleRatingBar) helper.getView(R.id.starbar);

        if (item.getImgs() != null) {
            if (item.getImgs().contains(",")) {
                String[] img = item.getImgs().split(",");
                for (int i = 0; i < img.length; i++) {
                    images.add(img[i]);
                }
            } else {
                images.add(item.getImgs());
            }
        }
        PhotoAdapter mPhotoAdapter = new PhotoAdapter(mContext, images);
        gv_photo.setAdapter(mPhotoAdapter);
        iv_content.setText(item.getContent());
        String isAnonymous = item.getUserId();
        if (TextUtils.isEmpty(isAnonymous)) {
            tv_name.setText("匿名用户");
            userhead.setImageResource(R.mipmap.ic_default_head);
        } else {
            tv_name.setText(item.getNickName());
            Glide.with(mContext).load(item.getHeadPortrait()).into(userhead);
        }
        tv_time.setText(item.getCreateTime().substring(0, 10));
        starbar.setRating((float) item.getScore());
        starbar.setEnabled(false);


    }
}
