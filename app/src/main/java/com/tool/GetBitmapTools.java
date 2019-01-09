package com.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.costans.PlatformContans;
import com.tool.slideshowview.BitmapCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * 获取网络图片
 * Created by zhush on 2016/7/14.
 */
public class GetBitmapTools {

    private static RequestQueue mQueue;//请求网络图片

    /**
     *
     * @param context
     * @param url
     * @param view
     * @param defaultBitmap    默认图
     * @param errorBitmap    加载失败的图
     */
   public static  void  getBitmap(Context context, String url, NetworkImageView view, int defaultBitmap, int errorBitmap){
        if(mQueue == null){
            mQueue = Volley.newRequestQueue(context);
        }

        ImageLoader imageLoader = new ImageLoader(mQueue, BitmapCache.getInstance());


       view.setDefaultImageResId(defaultBitmap);
       view.setErrorImageResId(errorBitmap);


       if(url.startsWith("http")){
           view.setImageUrl(url,imageLoader);
       }else {
           view.setImageUrl(PlatformContans.rootUrl+ url,imageLoader);
       }

    }

    /** 
          * 获取网络图片 
          * @param imageurl 图片网络地址 
          * @return Bitmap 返回位图 
          */
    public Bitmap getImageInputStream(String imgUrl){
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imgUrl);
            connection= (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置 
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
