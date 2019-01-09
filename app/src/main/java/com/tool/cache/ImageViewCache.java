package com.tool.cache;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;


import com.costans.Constans;
import com.tool.CommonImageTools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.WeakHashMap;

/**
 * 缓存
 * @author Administrator
 *
 */
public class ImageViewCache extends WeakHashMap<String, Bitmap>
{
	
	private static ImageViewCache mNetImageViewCache = new ImageViewCache();

	public static ImageViewCache getInstance()
	{
		return mNetImageViewCache;
	}

	/**
	 * 判断图片是否存在首先判断内存中是否存在然后判断本地是否存在
	 * 
	 * @param url
	 * @return
	 */
	public boolean isBitmapExit(String url) 
	{
		url= CommonImageTools.realWholeImageUrl(url);
		boolean isExit = containsKey(url);
		if (false == isExit) 
		{
			isExit = isLocalHasBmp(url);
		}
		return isExit;
	}

	/*
	 * 判断本地有没有
	 */
	private boolean isLocalHasBmp(String url) {
		boolean isExit = true;

		String name = changeUrlToName(url);
		String filePath = isCacheFileIsExit();

		File file = new File(filePath, name);
		if (file.exists()) {
			isExit = cacheBmpToMemory(file, url);
		} else {
			isExit = false;
		}
		return isExit;
	}
	/*
	 * 判断本地有没有
	 */
	public String isLocalHasBmpUrl(String url) 
	{
		url=CommonImageTools.realWholeImageUrl(url);
		if(url==null||url.equalsIgnoreCase(""))
		{
			return "";
		}
		String isExit = "";
		String name = changeUrlToName(url);
		String filePath = isCacheFileIsExit();

		File file = new File(filePath, name);
		if (file.exists()) 
		{
			isExit = file.getAbsolutePath();
		} 
		return isExit;
	}
	/*
	 * 将本地图片缓存到内存中
	 */
	private boolean cacheBmpToMemory(File file, String url) {
		boolean sucessed = true;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sucessed = false;
		}
		try 
		{
			byte[] bs = getBytesFromStream(inputStream);
			Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
			if (bitmap == null) {
				return false;
			}
			this.put(url, bitmap, false);
		} catch (Exception e) 
		{}catch (OutOfMemoryError e) {
		}
		return sucessed;
	}
	
	private byte[] getBytesFromStream(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while (len != -1) {
			try {
				len = inputStream.read(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (len != -1) {
				baos.write(b, 0, len);
			}
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	/*
	 * 判断缓存文件夹是否存在如果存在怎返回文件夹路径，如果不存在新建文件夹并返回文件夹路径
	 * 判断唯一
	 */
	public String isCacheFileIsExit() 
	{
		String filePath = "";
		String rootpath = "";
		if(isEnoughMem())
		{
			rootpath= Constans.FileLocation.FILE_SAVE_PATH;
			File dir = new File(rootpath); 
			if (!dir.exists())  
			{
				dir.mkdir();   
			}
		}else if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
		{
			//TODO 如果有SD卡
			rootpath = Constans.FileLocation.ESD;
			File dir = new File(rootpath); 
			if (!dir.exists())  
            {
                dir.mkdir();   
            }
			String basepath=Environment.getExternalStorageDirectory()+ Constans.FilePath.DIR_ROOT;
			File baseDir = new File(basepath); 
			if (!baseDir.exists())  
            {
				baseDir.mkdir();   
            }
		}
		filePath = rootpath + Constans.FilePath.DIR_IMG_PART;
		File file = new File(filePath);
		if (!file.exists())
		{
			file.mkdirs();
		}
		return filePath;
	}

	/*
	 * 将url变成图片的地址
	 */
	public String changeUrlToName(String url)
	{
		url=CommonImageTools.realWholeImageUrl(url);
		String name = url.replaceAll(":", "_");
		name = name.replaceAll("//", "_");
		name = name.replaceAll("/", "_");
		name = name.replaceAll("=", "_");
		name = name.replaceAll(",", "_");
		name = name.replaceAll("&", "_");
		return name;
	}

	public Bitmap put(String key, Bitmap value)
	{
		String filePath = isCacheFileIsExit();
		String name = changeUrlToName(key);
		File file = new File(filePath, name);
		//TODO 刘淇换背景图片用
		if (file.exists())
		{
			file.delete();
		}
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			value.compress(CompressFormat.PNG, 100, outputStream);
			outputStream.flush();
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", e.getMessage());
		} catch (IOException e1) {
			Log.e("IOException", e1.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (null != outputStream) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputStream = null;
		}

		return super.put(key, value);
	}
	/**
	 * 保存图片返回路径地址
	 * @param key
	 * @param value
	 * @param isCacheToLocal
	 * @param isReSave
	 * @return
	 */
	public String put(String key, Bitmap value, boolean isCacheToLocal,boolean isReSave)
	{
		String fileName = "";//图片路径名称
		String filePath = isCacheFileIsExit();
		String name = changeUrlToName(key);
		File file = new File(filePath, name);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			value.compress(CompressFormat.PNG, 100, outputStream);
			outputStream.flush();
			fileName = filePath+name;
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", e.getMessage());
		} catch (IOException e1) {
			Log.e("IOException", e1.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (null != outputStream) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputStream = null;
		}
		super.put(key, value);
		return fileName;
	}
	/**
	 * 判断内存是否小于10兆
	 * @return
	 */
	 private boolean isEnoughMem() 
	 {
         File path = Environment.getDataDirectory();  // Get the path /data, this is internal storage path.
         StatFs stat = new StatFs(path.getPath());
         long blockSize = stat.getBlockSize();
         long availableBlocks = stat.getAvailableBlocks();
         long memSize = availableBlocks* blockSize;  // free size, unit is byte.

         if (memSize <1024*1024*10) 
         { //If phone available memory is less than 10M , kill activity,it will avoid force when phone low memory.
                 return false;
         }
         return true;
	 } 
	/**
	 * 
	 * @param key
	 * @param value
	 * @param isCacheToLocal
	 *            是否缓存到本地
	 * @return
	 */
	public Bitmap put(String key, Bitmap value, boolean isCacheToLocal) 
	{
		if (isCacheToLocal) {
			return this.put(key, value);
		} else {
			return super.put(key, value);
		}
	}
}
