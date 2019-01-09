package com.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.widget.Gallery;


import com.costans.PlatformContans;
import com.tool.cache.ImageViewCache;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/***
 * 图片处理工具类
 *
 * @ClassName: JEREHCommonImageTools
 * @Description: TODO
 */
public class CommonImageTools {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;//请求码，自己定义


    /**
     * * Checks if the app has permission to write to device storage
     * *
     * * If the app does not has permission then the user will be prompted to
     * * grant permissions
     * *
     * * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

    /**
     * 获取本地图片数据信息
     *
     * @param url 本地图片地址
     ***/
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            Options options = new Options();
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap btp = BitmapFactory.decodeStream(fis, null, options);
            fis.close();
            return btp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取本地图片数据信息
     *
     * @param url 本地图片地址
     ***/
    public static Bitmap getScaleLoacalBitmap(Context ctx, String url, int width) {
        try {
            FileInputStream fis = new FileInputStream(url);
            Options options = new Options();
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap btp = BitmapFactory.decodeStream(fis, null, options);
            fis.close();
            return getScaleFixedBitmap(ctx, btp, width);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     ***/
    public static Bitmap getLoacalMinBitmap(Context ctx, String url, int x) {
        try {
            FileInputStream fis = new FileInputStream(url);
            Options options = new Options();
            options.inPreferredConfig = Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap btp = BitmapFactory.decodeStream(fis, null, options);
            fis.close();
            return getFixedBitmap(ctx, btp, x);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 从手机本地获得所有图片的path
     */
    private static Cursor cursor;

    public static List<String> getSDImageList(Context ctx) {
        List<String> bitPathList = new ArrayList<String>();
        try {
            if (ctx != null) {
                String[] projection = {Thumbnails._ID, Thumbnails.IMAGE_ID,
                        Thumbnails.DATA};
                cursor = ctx.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "_id DESC");
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        //int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                        //String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        //String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                        //String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                        //long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                        bitPathList.add(path);
                    }
                    cursor.close();
                }
            }
        } catch (Exception e) {
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
        }
        return bitPathList;
    }

    /***
     * 取照片数目
     *
     * @param ctx
     * @param fileCount
     * @return
     */
    public static List<String> getSDImageList(Context ctx, int fileCount) {
        List<String> bitPathList = new ArrayList<String>();
        try {
            if (ctx != null) {
                int tempCount = 0;
                cursor = ctx.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "_id DESC");
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext() && tempCount <= fileCount) {
                        tempCount++;
                        String ddd = MediaStore.Images.Media._ID;
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                        long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                        bitPathList.add(path);
                    }
                    cursor.close();
                }
            }
        } catch (Exception e) {
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
        }
        return bitPathList;
    }

    /**
     * 重建特定尺寸的bitmap
     *
     * @param x
     * @param y
     * @param image
     * @param outerRadiusRat
     * @param color
     * @return
     */
    public static Bitmap createFramedPhoto(Context ctx, int x, int y, Bitmap image, float outerRadiusRat, String color) {
        //根据源文件新建一个darwable对象  
        Drawable imageDrawable = null;
        Bitmap output = null;
        Canvas canvas = null;
        try {
            if (outerRadiusRat > 0) {
                outerRadiusRat = DpiTools.dip2px(ctx, outerRadiusRat);
            }
            imageDrawable = new BitmapDrawable(image);
            // 新建一个新的输出图片
            output = Bitmap.createBitmap(x, y, Config.ARGB_8888);
            canvas = new Canvas(output);
            // 新建一个矩形
            RectF outerRect = new RectF(0, 0, x, y);
            // 产生一个红色的圆角矩形
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);
            // 将源图片绘制到这个圆角矩形上
            //详解见http://lipeng88213.iteye.com/blog/1189452
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            imageDrawable.setBounds(0, 0, x, y);
            canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
            imageDrawable.draw(canvas);
            canvas.restore();
        } catch (Exception e) {
        } catch (OutOfMemoryError e) {
        }
        return output;
    }

    public static Drawable getDrawable(Context ctx, int x, int y, Bitmap image, float outerRadiusRat, String color) {
        //根据源文件新建一个darwable对象  
        Drawable imageDrawable = new BitmapDrawable(image);
        // 新建一个新的输出图片  
        Bitmap output = Bitmap.createBitmap(x, y, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        // 新建一个矩形  
        RectF outerRect = new RectF(0, 0, x, y);
        // 产生一个红色的圆角矩形  
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor(color));
        if (outerRadiusRat > 0) {
            outerRadiusRat = DpiTools.dip2px(ctx, outerRadiusRat);
        }
        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);
        // 将源图片绘制到这个圆角矩形上  
        //详解见http://lipeng88213.iteye.com/blog/1189452  
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        imageDrawable.setBounds(0, 0, x, y);
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();
        return imageDrawable;
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 返回图片质量bitmap
     *
     * @param configType
     * @return
     */
    public static Bitmap bitmapConfigType(int configType) {
        Bitmap bitmap = null;
        switch (configType) {
            case 1:
                bitmap = Bitmap.createBitmap(10, 10, Config.ARGB_8888);
                break;
            case 2:
                bitmap = Bitmap.createBitmap(10, 10, Config.ARGB_4444);
                break;
            case 3:
                bitmap = Bitmap.createBitmap(10, 10, Config.ALPHA_8);
                break;
            case 4:
                bitmap = Bitmap.createBitmap(10, 10, Config.RGB_565);
                break;
            default:
                bitmap = Bitmap.createBitmap(10, 10, Config.RGB_565);
                break;
        }
        return bitmap;
    }

    /**
     * 返回固定大小图片
     *
     * @param bmp
     * @return
     */
    public static Bitmap getFixedBitmap(Context ctx, Bitmap bmp, int x) {
        if (bmp != null) {
            float width = bmp.getWidth();// 获取真实宽高
            float height = bmp.getHeight();
            if (width > x) {
                height = (x / width) * height;
                width = x;
            }
            bmp = CommonImageTools.createFramedPhoto(ctx, (int) width, (int) height, bmp, 0, "#FFFFFF");
        }
        return bmp;
    }

    /**
     * 返回固定大小图片
     *
     * @param bmp
     * @return
     */
    public static Bitmap getScaleFixedBitmap(Context ctx, Bitmap bmp, int x) {
        if (bmp != null) {
            float width = bmp.getWidth();// 获取真实宽高
            float height = bmp.getHeight();
            if (width > x) {
                height = (x / width) * height;
                width = x;
            } else if (width < x) {
                height = (x / width) * height;
                width = x;
            }
            bmp = CommonImageTools.createFramedPhoto(ctx, (int) width, (int) height, bmp, 0, "#FFFFFF");
        }
        return bmp;
    }

    /**
     * 返回固定大小图片
     *
     * @param bmp
     * @return
     */
    public static Bitmap getFixedBitmap(Context ctx, Bitmap bmp) {
        if (bmp != null) {
            final float maxWidth = 640.0f;
            float width = bmp.getWidth();// 获取真实宽高
            float height = bmp.getHeight();
            if (width > maxWidth) {
                height = (maxWidth / width) * height;
                width = maxWidth;
            }
            bmp = CommonImageTools.createFramedPhoto(ctx, (int) width, (int) height, bmp, 0, "#FFFFFF");
        }
        return bmp;
    }

    /**
     * gallery滚动
     *
     * @param gal
     */
    public static void onFling(Gallery gal) {
        MotionEvent e1 = MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 89.333336f, 265.33334f, 0);
        MotionEvent e2 = MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 300.0f, 238.00003f, 0);
        gal.onFling(e1, e2, -1300, 0);
    }

    /**
     * 图片路径组装
     *
     * @param url
     * @return
     */
    public static String realWholeImageUrl(String url) {
        if (url == null || url.equals(""))
            return url;
        if (url.indexOf("http://") < 0) {
            url = PlatformContans.root + url;
        }
        //TODO
        return url;
    }

    /**
     * 图片路径组装
     *
     * @param url
     * @return
     */
    public static String realWholeImageUrlWithTopic(String url) {
        //TODO 根据URL判断手机内是否存在此照片，如果存在则返回本地SD卡图片路径类似file:///android_asset/igg.jpg
        return ImageViewCache.getInstance().isLocalHasBmpUrl(url);
    }

    /**
     * 返回固定大小图片
     *
     * @param bmp
     * @return
     */
    public static Bitmap getScaleFixedBitmapWithUpload(Bitmap bmp, int x) {
        if (bmp != null) {
            float width = bmp.getWidth();// 获取真实宽高
            float height = bmp.getHeight();
            if (width > x) {
                height = (x / width) * height;
                width = x;
            } else if (width < x) {
                height = (x / width) * height;
                width = x;
            }
            bmp = CommonImageTools.createFramedPhotoWithUpload((int) width, (int) height, bmp);
        }
        return bmp;
    }

    /**
     * 重建特定尺寸的bitmap
     *
     * @param x
     * @param y
     * @param image
     * @return
     */
    public static Bitmap createFramedPhotoWithUpload(int x, int y, Bitmap image) {
        //根据源文件新建一个darwable对象  
        Drawable imageDrawable = null;
        Bitmap output = null;
        Canvas canvas = null;
        try {
            imageDrawable = new BitmapDrawable(image);
            // 新建一个新的输出图片
            output = Bitmap.createBitmap(x, y, Config.RGB_565);
            canvas = new Canvas(output);
            // 新建一个矩形
            RectF outerRect = new RectF(0, 0, x, y);
            // 产生一个红色的圆角矩形
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(outerRect, 0, 0, paint);
            // 将源图片绘制到这个圆角矩形上
            //详解见http://lipeng88213.iteye.com/blog/1189452
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            imageDrawable.setBounds(0, 0, x, y);
            canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
            imageDrawable.draw(canvas);
            canvas.restore();
        } catch (Exception e) {
        } catch (OutOfMemoryError e) {
        }
        return output;
    }

    /**
     * 网络图片
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 网络图片
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitMap(String url, Rect outPadding, Options opts) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is, outPadding, opts);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 倍数缩放
     *
     * @param bitmap
     * @param times
     * @return
     */
    public static Bitmap matrixBitmap(Bitmap bitmap, float times) {
        Matrix matrix = new Matrix();
        matrix.postScale(times, times); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 压缩图片
     *
     * @param bitMap
     * @return
     */
    public static Bitmap imageZoom(Bitmap bitMap) {
        //图片允许最大空间   单位：KB
        double maxSize = 400.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     *          * 图片的缩放方法
     *          * 
     *          * @param bgimage
     *          *            ：源图片资源
     *          * @param newWidth
     *          *            ：缩放后宽度
     *          * @param newHeight
     *          *            ：缩放后高度
     *          * @return
     *          
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = createVideoThumbnail(videoPath, width,height);
        if (bitmap != null ){
            System.out.println("w" + bitmap.getWidth());
            System.out.println("h" + bitmap.getHeight());
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    private static Bitmap createVideoThumbnail(String url, int width, int height) {
                Bitmap bitmap = null;  
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;  
                try {  
                        if (Build.VERSION.SDK_INT >= 14) {
                                retriever.setDataSource(url, new HashMap<String, String>());
                            } else {  
                                retriever.setDataSource(url);  
                            }  
                        bitmap = retriever.getFrameAtTime();  
                    } catch (IllegalArgumentException ex) {  
                        // Assume this is a corrupt video file  
                    } catch (RuntimeException ex) {  
                        // Assume this is a corrupt video file.  
                    } finally {  
                        try {  
                                retriever.release();  
                            } catch (RuntimeException ex) {  
                                // Ignore failures while cleaning up.  
                            }  
                    }  
                if (kind == Thumbnails.MICRO_KIND && bitmap != null) {
                        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,  
                                        ThumbnailUtils.OPTIONS_RECYCLE_INPUT);  
                    }  
                return bitmap;  
            }  

//    /**
//     * Get file path
//     */
//    public static String getPath(Context ctx,Uri uri) {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = ctx.managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }


}