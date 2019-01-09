package com.tool;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 数值处理
 * @ClassName: JEREHCommNumTools 
 * @Description: TODO
 * @author Guo Jingbing
 * @date 2013-8-16 下午3:13:21
 */
public class CommNumTools
{
	/***
	 * 返回格式化整型
	 * @return
	 */
	public static int getFormatIntByBool(String bool)
	{
		try
		{
			return (bool.equalsIgnoreCase("true"))?1:0;
		}catch(Exception ex){return 0;}
	}
	/***
	 * 返回格式化整型
	 * @param o
	 * @return
	 */
	public static int getFormatInt(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Integer.parseInt(o.toString()):0;
		}catch(Exception ex){
		    ex.getStackTrace();
//	        JEREHDebugTools.Sysout(JEREHCommNumTools.class, "getFormatInt", Log.ERROR, ex);
		    return 0;
		}
	}
	/**
	 * 返回格式化整型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static int getFormatInt(Object o, int defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Integer.parseInt(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	/***
	 * 返回格式化DOUBLE型
	 * @param o
	 * @return
	 */
	public static double getFormatDouble(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Double.parseDouble(o.toString()):0;
		}catch(Exception ex){return 0.0;}
	}
	/**
	 * 数字转String 0返回""
	 * @Title: getFormatNumStr 
	 * @Description: TODO
	 * @param @param o
	 * @param @return    
	 * @return String   
	 * @throws
	 */
	public static String getFormatNumStr(Object o)
	{
        try
        {
            return (o != null && !o.toString().equals("")) ? o.toString(): "";
        }
        catch(Exception ex)
        {
            return "";
        }
	}
	/***
	 * 返回格式化DOUBLE型
	 * @param o
	 * @return
	 */
	public static int getFormatDoubleIntValye(Object o)
	{
		try
		{
			double v=(o!=null&&!o.toString().equals(""))? Double.parseDouble(o.toString()):0;
			return (int)v;
		}catch(Exception ex){return 0;}
	}
	/**
	 * 返回格式化DOUBLE型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static double getFormatDouble(Object o, double defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Double.parseDouble(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	/***
	 * 返回格式化Float型
	 * @param o
	 * @return
	 */
	public static float getFormatFloat(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Float.parseFloat(o.toString()):0;
		}catch(Exception ex){return 0.0f;}
	}
	/***
	 * 返回格式化Float型
	 * @param o
	 * @return
	 */
	public static int getFormatFloatIntValue(Object o)
	{
		try
		{
			float v=(o!=null&&!o.toString().equals(""))? Float.parseFloat(o.toString()):0;
			return (int)v;
		}catch(Exception ex){return 0;}
	}
	/**
	 * 返回格式化Float型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static float getFormatFloat(Object o, float defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Float.parseFloat(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	/***
	 * 返回格式化Float型
	 * @param o
	 * @return
	 */
	public static long getFormatLong(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Long.parseLong(o.toString()):0;
		}catch(Exception ex){return 0l;}
	}
	/**
	 * 返回格式化Float型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static long getFormatLong(Object o, long defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))? Long.parseLong(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	public static double getBigDecimal(double value)
	{
		return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	// 将字母转换成数字   
    public static int letterToNum(String input) {
        int outInt=0;
        try
        {
            outInt=getFormatInt16(input);
        }catch(NumberFormatException ex){
            ex.getStackTrace();
            StringBuffer sb=new StringBuffer();
            for (byte b : input.getBytes()) {  
                sb.append(b-96);
            }  
            outInt=getFormatInt16(sb.toString().replace("-", ""));
        }
        return outInt;
    }  

    /**
     * 转换小数，四舍五入保留设定位数小数
     * @Title: getBigDecimal 
     * @Description: TODO
     * @param @param value
     * @param @param length
     * @param @return    
     * @return double   
     * @throws
     */
    public static double getBigDecimal(double value,int length)
    {
        BigDecimal b   =   new BigDecimal(value);
        double   f  =   b.setScale(length,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }

    /***
     * 返回格式化整型
     * @param o
     * @return
     */
    public static int getFormatInt16(Object o)
    {
         return (o!=null&&!o.toString().equals(""))? Integer.parseInt(o.toString(),16):0;
    }
    /**
     *  判断数组中是否包含值，支持数值或字符串
     * @Title: isInArrays 
     * @Description: TODO
     * @param @param o
     * @param @return    
     * @return boolean   
     * @throws
     */
    public static boolean isInArrays(Object[] oArray, Object o)
    {
        for(int i = 0;i<oArray.length;i++)
        {
            if(oArray instanceof String[])
            {
                if(CommStrTool.getFormatStr(oArray[i]).equals(CommStrTool.getFormatStr(o)))
                {
                    return true;
                }
            }else{
                if(getFormatFloat(oArray[i])==getFormatFloat(o))
                {
                    return true;
                }
            } 
        }
        return false;
    }
    /**
     * List是否包含对象
     * @Title: isInList 
     * @Description: TODO
     * @param @param list
     * @param @param o
     * @param @return    
     * @return boolean   
     * @throws
     */
    public static boolean isInList(List<Object> list, Object o)
    {
        if(list==null||list.isEmpty()||o==null)
        {
            return false;
        }
        for(Object ol:list)
        {
//            System.out.println(o.toString()+">>>>>>>>"+ol.toString());
            if(ol instanceof String &&o instanceof String)
            {
                if(CommStrTool.getFormatStr(ol).equals(CommStrTool.getFormatStr(o)))
                {
                    return true;
                }
            }else{
                if(getFormatFloat(ol)==getFormatFloat(o))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 转换小数，保留小数点后length位
     * @Title: getFormatDoublePrecise 
     * @Description: TODO
     * @param @param o
     * @param @param format  .####
     * @param @return    
     * @return String   
     * @throws
     */
    public static String getFormatDoublePrecise(Object o, String format)
    {
    	double value=getFormatDouble(o);
    	DecimalFormat df = new DecimalFormat(format);
    	return df.format(value);
    }
	/**
	 *  把数字转换为周几字符串
	 * @Title: getWeekDayByNum
	 * @Description: TODO
	 * @param @param num
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getWeekDayByNum(int num){
		String str = "";
		switch (num){
			case 1:
				str = "周一";
				break;
			case 2:
				str = "周二";
				break;
			case 3:
				str = "周三";
				break;
			case 4:
				str = "周四";
				break;
			case 5:
				str = "周五";
				break;
			case 6:
				str = "周六";
				break;
			case 7:
				str = "周日";
				break;
		}
		return str;
	}

	static String[] units = {"","十","百","千","万","十万","百万","千万","亿","十亿","百亿","千亿","万亿" };
	static char[] numArray = {'零','一','二','三','四','五','六','七','八','九'};

	/**
	 * 将整数转换成汉字数字
	 * @param num 需要转换的数字
	 * @return 转换后的汉字
	 */
	public static String formatInteger(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					continue;
				} else {
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}
}
