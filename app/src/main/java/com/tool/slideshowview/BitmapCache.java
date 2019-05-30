package com.tool.slideshowview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.example.yunchebao.MyApplication;
import com.tool.cache.DiskLruCacheUtil;

import java.io.ByteArrayOutputStream;

/**
 * Created by fanzh on 2016/7/15.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    static BitmapCache bitmapCache;

    private LruCache<String, Bitmap> mCache;

    public static synchronized BitmapCache getInstance() {
        if (bitmapCache == null) {
            bitmapCache = new BitmapCache();
        }
        return bitmapCache;
    }

    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {

        if (mCache.get(url) == null) {
            byte[] bytes = DiskLruCacheUtil.readFromDiskCache(url, MyApplication.getInstance());
            Bitmap bitmap= Bytes2Bimap(bytes);
            if(bitmap!=null){
                mCache.put(url, bitmap);
            }
            return bitmap;
        } else {
            return mCache.get(url);
        }

    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
        DiskLruCacheUtil.writeToDiskCache(url, Bitmap2Bytes(bitmap), MyApplication.getInstance());
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public Bitmap Bytes2Bimap(byte[] b) {

        if (b!=null&&b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);

        } else {
            return null;
        }
    }

}