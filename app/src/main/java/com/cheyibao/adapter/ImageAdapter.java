package com.cheyibao.adapter;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.yunchebao.R;
import com.payencai.library.mediapicker.utils.ScreenUtils;
import com.tool.FileUtil;

import java.io.File;
import java.util.List;


public class ImageAdapter extends BaseMultiItemQuickAdapter<ImageAdapter.Self, BaseViewHolder> {

    public ImageAdapter(List<Self> imageList) {
        super(imageList);
        addItemType(Self.ADD_CLICK, R.layout.item_add_image);
        addItemType(Self.DISPLAY_IMAGE, R.layout.item_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, Self item) {
        int width = ScreenUtils.getScreenWidth(mContext);
        int space = ScreenUtils.dp2px(mContext, 60);
        int height = (width - space) / 4;
        helper.itemView.setLayoutParams(new GridLayoutManager.LayoutParams(height, height));
        helper.addOnClickListener(R.id.delete_photo_view);
        if (!TextUtils.isEmpty(item.url)) {
            Glide.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_comment));
        } else if (item.getUri() != null) {
            File fileByUri = FileUtil.getFileByUri(item.uri, mContext);
            Glide.with(mContext).load(fileByUri).into((ImageView) helper.getView(R.id.iv_comment));
        }
    }


    public static class Self implements MultiItemEntity {
        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public static final int ADD_CLICK = 1; //
        public static final int DISPLAY_IMAGE = 2; //

        public Self() {
            this.itemType = ADD_CLICK;
        }

        public Self(String url) {
            this.url = url;
            this.itemType = DISPLAY_IMAGE;
        }

        public Self(Uri uri) {
            this.uri = uri;
            this.itemType = DISPLAY_IMAGE;
        }

        private String url;
        private Uri uri;
        private int itemType;


        @Override
        public int getItemType() {
            return itemType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }


}
