package com.tool;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.costans.Constans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;

import io.rong.imageloader.utils.L;

import static com.autonavi.amap.mapcore.tools.GLFileUtil.getCacheDir;

/**
 * <b>文件工具类</b>
 * <br>
 * 
 * @author JerehSoft
 *
 * @version 1.0 BY 2014-06-12	一些操作文件的基本方法，包括外置卡及路径、对乱码的处理等<br>
 * 			1.1 BY 2014-07-04	打开文件，判断文件MIME类型<br>
 * 			1.2 BY 2014-07-06	保存文件<br>
 * 			1.3 BY 2014-07-10	删除文件<br>
 * 			1.4 BY 2014-08-19	增加文件解密<br>
 * 
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class FileUtil {

	/**
	 * 判断SD卡是否存在
	 * @return
	 */
	public static boolean isSDCardExist() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 获取外置目录根路径
	 * @return
	 */
	public static String getExternalRootPath() {
		String root = "";
		if (isSDCardExist()) {
			root = Environment.getExternalStorageDirectory() + File.separator;
		}
		return root;
	}
	
	/**
	 * 获取根路径
	 * @return
	 */
	public static String getInternalRootPath() {
		return Environment.getRootDirectory() + File.separator;
	}
	
	/**
	 * 格式化文件大小
	 * @param size
	 * @return
	 */
	public static String formatSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSize = "0B";
		if (size < 1024) {
			fileSize = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSize = df.format((double) size / 1024) + "KB";
		} else if (size < 1073741824) {
			fileSize = df.format((double) size / 1048576) + "MB";
		} else {
			fileSize = df.format((double) size / 1073741824) + "GB";
		}
		return fileSize;
	}
	
	/**
	 * 处理乱码
	 * 给定一个字符串，用getBytes("iso8859_1")
	 * 	1、如果b[i]有63，不用转码；
	 * 	2、如果b[i]全大于0，那么为英文字符串，不用转码；
	 * 	3、如果b[i]有小于0的，那么已经乱码，要转码
	 * 
	 * <br>
	 * 
	 * 还可能有乱码存在，后续再处理
	 * 
	 * @param s
	 * @return
	 */
	public static String formatEncoding(String s) {
		if (s == null)
			return "";
		String str = s;
		byte b[];
		try {
			b = s.getBytes("ISO-8859-1");
			for (int i = 0; i < b.length; i++) {
				if (b[i] == 63) 
					break;
				else if (b[i] > 0)
					continue;
				else if (b[i] < 0) 
					str = new String(b, "GB2312");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 打开文件
	 * 
	 * @param ctx
	 * @param path
	 */
	public static void openFile(Context ctx, String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				String type = getMIMEType(file);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(file), type);
				ctx.startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static File getFileByUri(Uri uri, Context context) {
		String path = null;
		if ("file".equals(uri.getScheme())) {
			path = uri.getEncodedPath();
			if (path != null) {
				path = Uri.decode(path);
				ContentResolver cr = context.getContentResolver();
				StringBuffer buff = new StringBuffer();
				buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
				Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
				int index = 0;
				int dataIdx = 0;
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
					index = cur.getInt(index);
					dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					path = cur.getString(dataIdx);
				}
				cur.close();
				if (index == 0) {
				} else {
					Uri u = Uri.parse("content://media/external/images/media/" + index);
					System.out.println("temp uri is :" + u);
				}
			}
			if (path != null) {
				return new File(path);
			}
		} else if ("content".equals(uri.getScheme())) {
			// 4.2.2以后
//			String[] proj = { MediaStore.Images.Media.DATA };
//			Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
//			if (cursor.moveToFirst()) {
//				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//				path = cursor.getString(columnIndex);
//			}
//			cursor.close();
//
//			return new File(path);

			return getFileFromContentUri(uri, context); //兼容Android 7.0+
		} else {
			//Log.i(TAG, "Uri Scheme:" + uri.getScheme());
		}
		return null;
	}



	private static File getFileFromContentUri(Uri contentUri, Context context) {
		if (contentUri == null) {
			return null;
		}
		File file = null;
		String filePath = "";
		String fileName = "";
		String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME,MediaStore.MediaColumns.TITLE};
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getColumnIndex(filePathColumn[0])>=0){
				filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
			}
			if (cursor.getColumnIndex(filePathColumn[1])>=0){
				fileName = cursor.getString(cursor.getColumnIndex(filePathColumn[1]));
			}
			cursor.close();
			if (!TextUtils.isEmpty(filePath)) {
				file = new File(filePath);
			}

			if (file==null || !file.exists() || file.length() <= 0 || TextUtils.isEmpty(filePath)) {
				filePath = getPathFromInputStreamUri(context, contentUri, fileName);
			}
			if (!TextUtils.isEmpty(filePath)) {
				file = new File(filePath);
			}
		}
		return file;
	}



	/**
	 * 用流拷贝文件一份到自己APP目录下
	 *
	 * @param context
	 * @param uri
	 * @param fileName
	 * @return
	 */
	public static String getPathFromInputStreamUri(Context context, Uri uri, String fileName) {
		InputStream inputStream = null;
		String filePath = null;

		if (uri.getAuthority() != null) {
			try {
				inputStream = context.getContentResolver().openInputStream(uri);
				File file = createTemporalFileFrom(context, inputStream, fileName);
				filePath = file.getPath();

			} catch (Exception e) {
				L.e(e);
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e) {
					L.e(e);
				}
			}
		}

		return filePath;
	}

	private static File createTemporalFileFrom(Context context, InputStream inputStream, String fileName)
			throws IOException {
		File targetFile = null;

		if (inputStream != null) {
			int read;
			byte[] buffer = new byte[8 * 1024];
			//自己定义拷贝文件路径
			targetFile = new File(getCacheDir(context), fileName);
			if (targetFile.exists()) {
				targetFile.delete();
			}
			OutputStream outputStream = new FileOutputStream(targetFile);

			while ((read = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
			}
			outputStream.flush();

			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return targetFile;
	}



	/**
	 * 解密并打开文件
	 * 
	 * @param ctx
	 * @param path
	 */
	public static void decipherFile(Context ctx, String path) {
		try {
			SharedPreferences LPreferences = ctx.getSharedPreferences(Constans.Preference.PREFERENCE_LOCATION, Context.MODE_PRIVATE);
			String resRoot = LPreferences.getString(Constans.Preference.PREFERENCE_RES, "");
			if (resRoot == null || resRoot.trim().length() == 0) {
				resRoot = Environment.getExternalStorageDirectory() + "/" + Constans.FilePath.DIR_ROOT;
			}
			File file = new File(path);
			if (file.exists()) {
				String tempPath = resRoot + Constans.FilePath.TEMP_RES + path.substring(path.lastIndexOf("/") + 1);
				File realFile = new File(tempPath);
				if (!realFile.getParentFile().exists()) {
					realFile.getParentFile().mkdirs();
				}
				if (!realFile.exists()) {
					realFile.createNewFile();
				} else {
					realFile.delete();
					realFile.createNewFile();
				}
				RandomAccessFile accessFile = new RandomAccessFile(realFile, "rwd");
				byte[] buffer = new byte[1024 * 8];
				int offset = (int) (file.length()/2 + file.length()%2);
				
				RandomAccessFile readFile = new RandomAccessFile(file, "r");
				if(file.length() % 2 == 1) {
					readFile.seek(offset - 1);
				} else {
					readFile.seek(offset);
				}
				int of = -1;
				while ((of = readFile.read(buffer)) != -1) {
					accessFile.write(buffer, 0, of);
				}
				readFile.seek(0);
				int writePoint;
				//奇数长度的文件注意中间的字符不要重复读取；
				if(file.length() % 2 == 1){
					writePoint = offset - 1;
				} else {
					writePoint = offset;
				}
				if(writePoint < buffer.length)
					buffer = new byte[writePoint];
				while ((of = readFile.read(buffer)) != -1) {
					accessFile.write(buffer, 0, of);
					writePoint -= of;
					if(writePoint <= buffer.length)
						break;
				}
				byte[] b2 = new byte[writePoint];
				readFile.read(b2);
				accessFile.write(b2, 0, b2.length);
				readFile.close();
				accessFile.close();
				
				String type = getMIMEType(realFile);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(realFile), type);
				ctx.startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 解密并打开文件
	 * 
	 * @param ctx
	 * @param path
	 */
	public static HashMap<String, Object> decipherFileReturnPath(Context ctx, String path, String filetype) {
		try {
			HashMap<String, Object> map= new HashMap<String, Object>();
			SharedPreferences LPreferences = ctx.getSharedPreferences(Constans.Preference.PREFERENCE_LOCATION, Context.MODE_PRIVATE);
			String resRoot = LPreferences.getString(Constans.Preference.PREFERENCE_ROOT, "");
			if (resRoot == null || resRoot.trim().length() == 0) {
				resRoot = Environment.getExternalStorageDirectory() + "/" + Constans.FilePath.DIR_ROOT;
			}
			File file = new File(path);
			if (file.exists()) {
				String tempPath = resRoot + Constans.FilePath.TEMP_RES + path.substring(path.lastIndexOf("/") + 1) + filetype;
				File realFile = new File(tempPath);
				if (!realFile.getParentFile().exists()) {
					realFile.getParentFile().mkdirs();
				}
				if (!realFile.exists()) {
					realFile.createNewFile();
				} else {
					realFile.delete();
					realFile.createNewFile();
				}
				RandomAccessFile accessFile = new RandomAccessFile(realFile, "rwd");
				byte[] buffer = new byte[1024 * 8];
				int offset = (int) (file.length()/2 + file.length()%2);
				
				RandomAccessFile readFile = new RandomAccessFile(file, "r");
				if(file.length() % 2 == 1) {
					readFile.seek(offset - 1);
				} else {
					readFile.seek(offset);
				}
				int of = -1;
				while ((of = readFile.read(buffer)) != -1) {
					accessFile.write(buffer, 0, of);
				}
				readFile.seek(0);
				int writePoint;
				//奇数长度的文件注意中间的字符不要重复读取；
				if(file.length() % 2 == 1){
					writePoint = offset - 1;
				} else {
					writePoint = offset;
				}
				if(writePoint < buffer.length)
					buffer = new byte[writePoint];
				while ((of = readFile.read(buffer)) != -1) {
					accessFile.write(buffer, 0, of);
					writePoint -= of;
					if(writePoint <= buffer.length)
						break;
				}
				byte[] b2 = new byte[writePoint];
				readFile.read(b2);
				accessFile.write(b2, 0, b2.length);
				readFile.close();
				accessFile.close();
				String type = getMIMEType(realFile);
				map.put("realFile", realFile);
				map.put("type", type);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null; 
	}
		
	/**
	 * 判断文件的MINETYPE
	 * 
	 * @param f
	 * @return
	 */
	public static String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		String end = fName.substring(fName.lastIndexOf("."), fName.length()).toLowerCase();
		for (int i = 0; i < MIMEType.MIMEType.length; i++) {
			if (MIMEType.MIMEType[i][0].equals(end)) {
				type = MIMEType.MIMEType[i][1];
				break;
			}
		}
		/* 如果无法直接打开，就跳出软件列表给用户选择 */
		if (type.trim().length() == 0) {
			type = "/*";
		}
		return type;
	}
	
	/**
	 * 保存文件到SD卡
	 * @param path
	 * @param name
	 * @param content
	 */
	public static void saveFileToSD(String path, String name, String content) {
		File dir = new File(path);
		if (!dir.exists()) 
			dir.mkdirs();
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(path + name);
			osw = new OutputStreamWriter(fos, "utf-8");
			osw.write(content);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null) osw.close();
				if (fos != null) fos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/*
	 * 创建文件夹
	 */
	public static void createFileDir(String filePath) {
		File dir = new File(filePath);
		if (!dir.exists()) 
			dir.mkdirs();
	}
	
	/**
	 * 保存文件到SD卡
	 * @param file
	 * @param content
	 */
	public static void saveFileToSD(File file, String content) {
		String filePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("/") + 1);
		File dir = new File(filePath);
		if (!dir.exists()) 
			dir.mkdirs();
		
		try {
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "utf-8");
			osw.write(content);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null) osw.close();
				if (fos != null) fos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static File saveBitmapToFileOnSD(Bitmap bm, String dirFullPath, String picName) {
		try {
//			String dirFullPath = Environment.getExternalStorageDirectory() + "/" + FilePath.DIR_ROOT + dirPath + "/";
			File dir = new File(dirFullPath);
			if (!dir.exists()) 
				dir.mkdirs();
			File f = new File(dirFullPath, picName + ".jpg");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			return f;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }
 
	//添加水印
	public static Bitmap drawTextToBitmap(Context gContext, Bitmap bitmap, String gText) {
		try {
			String dataStr = "拍照时间:" + CommonDateTools.getCurrentDate("yyyy-MM-dd HH:mm:ss");
			Resources resources = gContext.getResources();
			float scale = resources.getDisplayMetrics().density;
			Bitmap.Config bitmapConfig = bitmap.getConfig();
			// set default bitmap config if none
			if (bitmapConfig == null) {
				bitmapConfig = Bitmap.Config.ARGB_8888;
			}
			// resource bitmaps are imutable,
			// so we need to convert it to mutable one
			bitmap = bitmap.copy(bitmapConfig, true);
			Canvas canvas = new Canvas(bitmap);
			//添加水印title
			// new antialised Paint
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.BLUE);
			paint.setTextSize((int) (8 * scale));
			paint.setDither(true); // 获取跟清晰的图像采样
			paint.setFilterBitmap(true);// 过滤一些
			Rect bounds = new Rect();
			paint.getTextBounds(gText, 0, gText.length(), bounds);
			int x = 5;
			int y = 15;
			canvas.drawText(gText, x * scale, y * scale, paint);
			//添加水印时间
			// new antialised Paint
			Paint dataPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			// text color - #3D3D3D
			dataPaint.setColor(Color.BLUE);
			dataPaint.setTextSize((int) (8 * scale));
			dataPaint.setDither(true); // 获取跟清晰的图像采样
			dataPaint.setFilterBitmap(true);// 过滤一些
			Rect dataBounds = new Rect();
			dataPaint.getTextBounds(dataStr, 0, dataStr.length(), dataBounds);
			int data_x = 5;
			int data_y = 30;
			canvas.drawText(dataStr, data_x * scale, data_y * scale, paint);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 递归删除文件和文件夹
	 * @param file 要删除的根目录
	 */
	public static void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider    
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider    
            if (isExternalStorageDocument(uri)) {    
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
        
                if ("primary".equalsIgnoreCase(type)) {    
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }    
            }    
            // DownloadsProvider    
            else if (isDownloadsDocument(uri)) {    
        
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
        
                return getDataColumn(context, contentUri, null, null);    
            }    
            // MediaProvider    
            else if (isMediaDocument(uri)) {    
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
        
                Uri contentUri = null;
                if ("image".equals(type)) {    
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {    
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {    
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }    
        
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]    
                };    
        
                return getDataColumn(context, contentUri, selection, selectionArgs);    
            }    
        }    
        // MediaStore (and general)    
        else if ("content".equalsIgnoreCase(uri.getScheme())) {    
            return getDataColumn(context, uri, null, null);    
        }    
        // File    
        else if ("file".equalsIgnoreCase(uri.getScheme())) {    
            return uri.getPath();    
        }    
        
        return null;    
    }    
        
    /**   
     * Get the value of the data column for this Uri. This is useful for   
     * MediaStore Uris, and other file-based ContentProviders.   
     *   
     * @param context The context.   
     * @param uri The Uri to query.   
     * @param selection (Optional) Filter used in the query.   
     * @param selectionArgs (Optional) Selection arguments used in the query.   
     * @return The value of the _data column, which is typically a file path.   
     */    
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column    
        };    
        
        try {    
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,    
                    null);    
            if (cursor != null && cursor.moveToFirst()) {    
                final int column_index = cursor.getColumnIndexOrThrow(column);    
                return cursor.getString(column_index);    
            }    
        } finally {    
            if (cursor != null)    
                cursor.close();    
        }    
        return null;    
    }    
        
        
    /**   
     * @param uri The Uri to check.   
     * @return Whether the Uri authority is ExternalStorageProvider.   
     */    
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());    
    }    
        
    /**   
     * @param uri The Uri to check.   
     * @return Whether the Uri authority is DownloadsProvider.   
     */    
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());    
    }    
        
    /**   
     * @param uri The Uri to check.   
     * @return Whether the Uri authority is MediaProvider.   
     */    
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());    
    } 
}