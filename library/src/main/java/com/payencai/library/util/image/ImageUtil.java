package com.payencai.library.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
    static File compressImage(File imageFile, long size, int reqWith, int reqHeight, Bitmap.CompressFormat compressFormat,
                              int quantity, String destinationPath) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File(destinationPath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(destinationPath);
            //先压缩尺寸，防止内存溢出
            Bitmap bitmap = decodeSampledBitmapFromFile(imageFile, reqWith, reqHeight);
            //ByteArrayOutputStream 不需要关闭
            //压缩尺寸
            ByteArrayOutputStream baos = compressBitmapSize(bitmap, compressFormat, quantity, size);
            fileOutputStream.write(baos.toByteArray());
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(destinationPath);
    }

    //按照比例压缩图片
    static Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //just need size
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //real decode bitmap
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //rotation of the image
        ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }
        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
                scaledBitmap.getHeight(), matrix, true);

        return scaledBitmap;
    }

    //获取采样（压缩比）
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            while ((height / inSampleSize) >= reqHeight || (width / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    //压缩尺寸
    private static ByteArrayOutputStream compressBitmapSize(Bitmap bitmap, Bitmap.CompressFormat compressFormat,
                                                            int defaultQuality, long maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = defaultQuality;
        bitmap.compress(compressFormat, quality, baos);
        while (baos.toByteArray().length / 1024 > maxSize) {
            if (quality <= 10) { // quality must >0
                break;
            } else {
                baos.reset();
                quality -= 10;
                bitmap.compress(compressFormat, quality, baos);
            }
        }
        return baos;
    }

}
