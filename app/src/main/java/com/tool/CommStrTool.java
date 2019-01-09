package com.tool;


import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * 文本内容处理
 *
 * @ClassName: CommonStrTools
 */
public class CommStrTool {
    /**
     * 根据传入对象返回格式化后的字符串值
     **/
    public static String getFormatStr(Object o) {
        String str = "";
        try {
            if (o == null) {
                return "";
            }
            str = QBchange(o.toString().trim());
        } catch (Exception ex) {
        }
        return str;

    }

    /**
     * 根据传入对象返回格式化后的字符串值
     **/
    public static boolean getFormatBoolean(Object o) {
        boolean result = false;
        try {
            if (o == null) {
                return false;
            } else {
                if (o.toString().equalsIgnoreCase("true") || o.toString().equalsIgnoreCase("1")) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }
        return result;

    }

    /***
     * 大写前加下划线
     *
     * @param str
     * @return
     */
    public static String replaceXHX(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; c != null && i < c.length; i++) {
            if (c[i] < 97 && i != 0) {
                sb.append(("_" + c[i]));
            } else {
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    /***
     * 大写换小写前加下划线
     *
     * @param str
     * @return
     */
    public static String replaceLowerCase(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; c != null && i < c.length; i++) {
            if (c[i] < 97 && c[i] > 57) {
                sb.append(("_" + c[i]).toLowerCase());
            } else {
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    /***
     * 大写换小写前加下划线
     *
     * @param str
     * @return
     */
    public static String replaceUpCase(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; c != null && i < c.length; i++) {
            if (i == 0) {
                sb.append(("" + c[i]).toUpperCase());
            } else if (c[i] < 97) {
                sb.append(("_" + c[i]));
            } else {
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    /***
     * 转换回去
     *
     * @param str
     * @return
     */
    public static String replaceUpperCase(String str) {
        StringBuffer sb = new StringBuffer();
        String[] c = str.split("_");
        for (int i = 0; c != null && i < c.length; i++) {
            if (i > 0) {
                sb.append(c[i].substring(0, 1).toUpperCase() + c[i].substring(1));
            } else {
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    /***
     * 按照位置替换字符串
     *
     * @param str        目标字符串
     * @param replaceStr 要被替换的字符串
     * @param expressStr 要被替换的字符
     * @param position   要被替换的位置
     * @return
     */
    public static String replaceByPosition(String str, String replaceStr, String expressStr, int position) {
        StringBuffer sb = new StringBuffer();
        String[] c = str.split(expressStr);
        for (int i = 0; c != null && i < c.length; i++) {
            if (i == position) {
                c[i] = str;
            }
            sb.append(c[i]);
        }
        return sb.toString();
    }

    /**
     * 全角转半角
     *
     * @param str
     * @return
     */
    public static final String QBchange(String str) {
        if (str == null) {
            return "";
        }
        String outStr = "";
        String tStr = "";
        byte[] b = null;
        for (int i = 0; str != null && i < str.length(); i++) {
            try {
                tStr = str.substring(i, i + 1);
                b = tStr.getBytes("unicode");
            } catch (Exception ex) {
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (Exception ex) {
                }
            } else {
                outStr = outStr + tStr;
            }
        }
        return outStr;
    }

    /**
     * 校验联系方式  --OK
     *
     * @param phone 手机/电话号码
     * @param type  验证类型 1：手机   2：电话
     **/
    public static boolean checkPhoneOrMobie(String phone, Integer type) {
        boolean result = false;
        try {
            /*
			 * 	移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、182
				联通：130、131、132、152、155、156、185、186
				电信：133、153、180、189、（1349卫通）
			 * */
            switch (type) {
                case 1:
//					result  =checkResult(phone,"^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
                    result = checkResult(phone, "^[\\s\\u3000]*(((\\([+]\\d{1,2}\\))|([+]\\d{1,2}))|0)?1\\d{10}[\\s\\u3000]*$");
                    break;
                case 2://
//					result  =checkResult(phone, "^[\\s\\u3000]*((\\d{3,4})|(\\(\\d{3,4}\\)))?(\\d{6,8})(\\d{1,5})?[\\s\\u3000]*$");
                    result = checkResult(phone, "^[\\s\\u3000]*((\\d{3,4}-?)|(\\(\\d{3,4}\\)))?(\\d{6,8})(-\\d{1,5})?[\\s\\u3000]*$");
//					if(!result&&!checkPhoneOrMobie(phone,1))
//					{
//						result= checkResult(phone, "^[\\s\\u3000]*((\\d{3,4})|(\\(\\d{3,4}\\)))?(\\d{6,8})(\\d{1,5})?[\\s\\u3000]*$");
//					}
                    break;
                case 3:
//					result  =checkResult(phone,"^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                    result = checkResult(phone, "^[\\s\\u3000]*(((\\([+]\\d{1,2}\\))|([+]\\d{1,2}))|0)?1\\d{10}[\\s\\u3000]*$");
                    if (!result) {
//						result  =checkResult(phone, "^[\\s\\u3000]*((\\d{3,4})|(\\(\\d{3,4}\\)))?(\\d{6,8})(\\d{1,5})?[\\s\\u3000]*$");
                        result = checkResult(phone, "^[\\s\\u3000]*((\\d{3,4}-?)|(\\(\\d{3,4}\\)))?(\\d{6,8})(-\\d{1,5})?[\\s\\u3000]*$");
                    }
//					if(!result)
//					{
//						result= checkResult(phone, "^[\\s\\u3000]*((\\d{3,4})|(\\(\\d{3,4}\\)))?(\\d{6,8})(\\d{1,5})?[\\s\\u3000]*$");
//					}
                    break;
            }

        } catch (Exception ex) {
        }
        return result;
    }

    public static boolean checkResult(String str, String pattenStr) {
        Pattern p = Pattern.compile(pattenStr);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * UUID
     **/
    public static String createUUID(boolean isUp) {
        String uuidStr = java.util.UUID.randomUUID().toString();
        if (isUp) {
            uuidStr.toLowerCase();
        }
        return uuidStr;
    }

//    /***
//     * 根据传入的字符串以及分隔符来或者数组
//     *
//     * @param str
//     * @param spilt
//     * @return
//     */
//    public static String[] getArrayBySpilt(String str, String spilt) {
//        String[] array = null;
//        try {
//            array = str.split("\\" + spilt);
//        } catch (Exception ex) {
//        }
//        return array;
//    }



    public static String convertPhone(String phone) {
        String newPhone = "";
        if (checkPhoneOrMobie(phone, 1)) {
            newPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        }
        return newPhone;
    }

    public static String convertCall(String call, int tag) {
        String newPhone = "";
        if (checkPhoneOrMobie(call, 2)) {
            switch (tag) {
                case 1:
                    newPhone = call.substring(0, 3) + "-" + call.substring(3);
                    break;
                case 2:
                    newPhone = call.substring(0, 4) + "-" + call.substring(4);
                    break;
            }
        }
        return newPhone;
    }


    /**
     * Like功能
     **/
    public static boolean likeFUC(String str, String pattenStr) {
        boolean result = false;
        if (str != null && !str.equals("")) {
            Matcher m = Pattern.compile(pattenStr, Pattern.CASE_INSENSITIVE).matcher(str);
            while (m.find()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Like功能加强版
     **/
    public static boolean likeFUCMore(String str, String[] pattenStr) {
        boolean result = false;
        if (str != null && !str.equals("")) {
            String[] strArray = new String[str.length()];
            for (int i = 0; i < str.length(); i++) {
                strArray[i] = str.substring(i, i + 1);
            }
            for (int i = 0; i < pattenStr.length; i++) {
                for (int j = 0; j < strArray.length; j++) {
                    if (pattenStr[i].equalsIgnoreCase(strArray[j])) {
                        return true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获得base64编码
     *
     * @param bitmap
     * @return
     */
    public static byte[] getFileByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, baos);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return baos.toByteArray();
    }

    /**
     * 获得压缩后图片流
     *
     * @param @param  bitmap
     * @param @param  qWeight 压缩程度
     * @param @return
     * @return ByteArrayInputStream
     * @throws
     * @Title: getFileInput
     * @Description: TODO
     */
    public static ByteArrayInputStream getFileInput(Bitmap bitmap, int qWeight) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, baos);
        byte[] temp = baos.toByteArray();
        if (temp.length > 2000000) {
            qWeight = 10;
        } else if (temp.length > 500000) {
            qWeight = 20;
        } else if (temp.length < 100000) {
            qWeight = 50;
        }
        temp = null;
//        //Log.i("Systemout", "压缩比：" + qWeight);
        baos = new ByteArrayOutputStream();
        //第二个参数100为不压缩
        bitmap.compress(CompressFormat.JPEG, qWeight, baos);
        byte[] picBytes = baos.toByteArray();
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        ByteArrayInputStream ins = new ByteArrayInputStream(picBytes);
        picBytes = null;
        return ins;
    }
    /**
     * 获得位图编码
     * @return
     */
    /**
     * 获取本地图片数据信息
     *
     * @param url 本地图片地址
     ***/
    public static Bitmap getLoacalBitmap(String url) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTempStorage = new byte[16 * 1024];
            options.inPurgeable = true;
            options.inSampleSize = 2;
            options.inInputShareable = true;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(url, options);
            options.inSampleSize = computeInitialSampleSize(options, -1, 300 * 300);
            //这里一定要将其设置回false，因为之前我们将其设置成了true
            options.inJustDecodeBounds = false;
            Bitmap btp = BitmapFactory.decodeFile(url, options);
            options = null;
            return btp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}