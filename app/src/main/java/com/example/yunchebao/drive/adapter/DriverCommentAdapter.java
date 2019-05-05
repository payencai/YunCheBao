package com.example.yunchebao.drive.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.drive.model.SubstitubeComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.payencai.library.view.CircleImageView;
import com.vipcenter.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/2/26 11:15
 * 邮箱：771548229@qq..com
 */
public class DriverCommentAdapter extends BaseQuickAdapter<SubstitubeComment,BaseViewHolder> {
    public DriverCommentAdapter(int layoutResId, @Nullable List<SubstitubeComment> data) {
        super(layoutResId, data);
    }
    PhotoAdapter mPhotoAdapter;
    List<String> images;
    @Override
    protected void convert(BaseViewHolder helper, SubstitubeComment item) {
        CircleImageView iv_head= (CircleImageView) helper.getView(R.id.userhead);
        CircleImageView coashhead= (CircleImageView) helper.getView(R.id.coashhead);
        GridView gv_photo= (GridView) helper.getView(R.id.gv_photo);
        TextView tv_name= (TextView) helper.getView(R.id.tv_name);
        TextView tv_coashname= (TextView) helper.getView(R.id.tv_coashname);
        TextView tv_time= (TextView) helper.getView(R.id.tv_time);
        SimpleRatingBar simpleRatingBar= (SimpleRatingBar) helper.getView(R.id.sb_score);
        TextView tv_content= (TextView) helper.getView(R.id.tv_content);
        tv_name.setText(item.getName());
        tv_coashname.setText(item.getDriverName());
        tv_content.setText(item.getContent());
        tv_time.setText(item.getCreateTime().substring(0,10));
        simpleRatingBar.setRating((float) item.getDriverScore());
        images=new ArrayList<>();
        if(!TextUtils.isEmpty(item.getImgs())){
            if(item.getImgs().contains(",")){
                String[] img=item.getImgs().split(",");
                for (int i = 0; i <img.length ; i++) {
                    images.add(img[i]);
                }
            }else{
                images.add(item.getImgs());
            }
        }
        mPhotoAdapter=new PhotoAdapter(mContext,images);
        gv_photo.setAdapter(mPhotoAdapter);
        Glide.with(mContext).load(item.getDriverHeadPortrait()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(coashhead);
        Glide.with(mContext).load(item.getHeadPortrait()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(iv_head);
    }
}
