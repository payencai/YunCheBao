package com.tool;

import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonDateTools
{
    /**
     * 格式化日期
     * @param dateStr
     * @return
     */
    public static Date convertToDate(String dateStr)
    {    
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date=new Date();
        try
        {
        	date =sdf.parse(dateStr);
        } catch (Exception ex){
        	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        	try {
				date =format.parse(dateStr);
			} catch (ParseException e) {
	        	SimpleDateFormat format1= new SimpleDateFormat("yyyyMMddHHmm");
	        	try {
					date =format1.parse(dateStr);
				} catch (ParseException e1) {
//		        	JEREHDebugTools.Sysout(JEREHCommonDateTools.class, "convertToDate1", Log.ERROR, ex);
				}
			}
        }
        return date;
    }
    /**
     * 格式化日期
     * @param dateStr
     * @param format
     * @return
     */
    public static Date convertToDate(String dateStr, String format)
    {    
        SimpleDateFormat sdf= new SimpleDateFormat(format);
    	Date date=new Date();
        try
        {
        	date =sdf.parse(dateStr);
        } catch (Exception ex){
//        	JEREHDebugTools.Sysout(JEREHCommonDateTools.class, "Date convertToDate", Log.ERROR, ex);
        }
        return date;
    }
    /**
     * 格式化日期
     * @param dateStr
     * @return
     */
    public static String convertDate(String dateStr, String format)
    {    
    	String date="";
    	if(!dateStr.equals(""))
    	{
            try {
                Date date1 = convertToDate(dateStr);
//                = DateFormat.getDateInstance().parse(dateStr);
                date = getFormatDate(format, date1);
            } catch (Exception ex) {
                Log.e("Systemout",dateStr+"转型"+format+"失败:"+ex.toString());
            }
    	}
        return date;
    }
    public static String getFormatDate(String format, Date d) {
		String today = "";
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date now = new Date(d.getTime());
			today = sdf.format(now);
			sdf = null;
			now = null;
		} catch (Exception e) {
		}
		return today;
	}
    /***
     * 获得当前时间之后n天时间
     * @param format
     * @param days
     * @return
     * author:GuoJingbing
     */
    public static String getAfterDate(String format, int days)
    {
		String newDate = "";
		Calendar cal = Calendar.getInstance();
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
	        cal.add(Calendar.DAY_OF_MONTH, days);
			newDate = sdf.format(cal.getTime());
			sdf = null;
		} catch (Exception e) {
		}
		return newDate;
	}    
    
    /***
     * 获得当前时间
     * @param format
     * @return
     */
    public static String getCurrentDate(String format)
    {
		String today = "";
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date now = new Date();
			today = sdf.format(now);
			sdf = null;
			now = null;
		} catch (Exception e) {
		}
		return today;
	}
    /**
     * 比较两个日期大小
     * @return
     */
    public static int compareDate(String fDate, String tDate, String format)
    {    
        DateFormat df = new SimpleDateFormat(format);
        try
        {
            Date dt1 = df.parse(fDate);
            Date dt2 = df.parse(tDate);
            if (dt1.getTime()>dt2.getTime()) 
            {
                return 3;
            } else if (dt1.getTime()<dt2.getTime())
            {
                return 1;
            } else if(dt1.getTime()==dt2.getTime()) 
            {
                return 2;
            }else
            {
            	return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /**
     * 比较两个日期大小
     * @return
     */
    public static long compareDateTwo(String fDate, String tDate, String format)
    {    
        DateFormat df = new SimpleDateFormat(format);
        try
        {
            Date dt1 = df.parse(fDate);
            Date dt2 = df.parse(tDate);
            long a = (long) ((dt1.getTime()-dt2.getTime())/(3600*24*1000));
           	return a;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
		return 0;
    }
    /***
     * 获得当前时间
     * @param format
     * @return
     */
    public static String dayAdd(String format, String dateStr, int dayAdd)
    {
		String today = "";
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dt1 = sdf.parse(dateStr);
			dt1.setDate(dt1.getDate()+dayAdd);
			today = sdf.format(dt1);
			sdf = null;
			dt1 = null;
		} catch (Exception e) {
		}
		return today;
	}

    /**
     * 格式化日期
     * @return
     */
    public static String convertDateToText(Timestamp date)
    {    
        String mothText="";
        try 
        {
            long time=getCurrentTimestampDate().getTime()-date.getTime();
            //TODO 如果为同一天
            if(isTheSameDate(getCurrentTimestampDate(),date))
            {
                //如果过5小是以内
                if(time<((1000*60*60*24)))
                {
                    //如果1小时以内
                    if (time<((1000*60*60))){
                        //如果一分钟以内
                        if(time<((1000*60*1)))
                        {
                            mothText="刚刚";
                        }else
                        {
//                            if(time%60000==0)
//                            {
//                                mothText=time/60000+"分钟前";
//                            }else
//                            {
                                mothText=(time/60000)+"分钟前";
//                            }
                        }
                    }else {

                        mothText=time/(60000*60)+"小时前";

                    }
                }else
                {
                    mothText=getHHMM(date);
                }
            }else
            {
//                if(time<((1000*60*60*24)*3))
//                {
//                    if(time<((1000*60*60*24)*2))
//                    {
//                        mothText="昨天"+getHHMM(date);
//                    }else
//                    {
//                        mothText="前天"+getHHMM(date);
//                    }
//                }else
//                {
//                    mothText=JEREHCommonDateTools.getFormatDate("yyyy-MM-dd HH:mm", date);
//                }


                mothText=CommonDateTools.getFormatDate("yyyy-MM-dd HH:mm", date);
            }
        } catch (Exception exception) {}
        return mothText;
    }

    public static Timestamp getCurrentTimestampDate()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    public static String getHHMM(Timestamp date)
    {
        int h=date.getHours();
        if(h>=8&&h<12)
        {
            return "上午"+CommonDateTools.getFormatDate("HH:mm", date);
        }else if(h>=12&&h<13)
        {
            return "中午"+CommonDateTools.getFormatDate("HH:mm", date);
        }else if(h>=13&&h<18)
        {
            return "下午"+CommonDateTools.getFormatDate("HH:mm", date);
        }else if(h>=18&&h<24)
        {
            return "晚上"+CommonDateTools.getFormatDate("HH:mm", date);
        }else if(h>=0&&h<6)
        {
            return "凌晨"+CommonDateTools.getFormatDate("HH:mm", date);
        }else
        {
            return "早上"+CommonDateTools.getFormatDate("HH:mm", date);
        }
    }
    /**
     * TODO 判断两个时间戳(Timestamp)是否在同一天
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isTheSameDate(Timestamp time1, Timestamp time2 )
    {
     if(time1!=null&&time2!=null)
     {
         Calendar c1= Calendar.getInstance();
         c1.setTime(time1);
         int y1=c1.get(Calendar.YEAR);
         int m1=c1.get(Calendar.MONTH);
         int d1=c1.get(Calendar.DATE);
         Calendar c2= Calendar.getInstance();
         c2.setTime(time2);
         int y2=c2.get(Calendar.YEAR);
         int m2=c2.get(Calendar.MONTH);
         int d2=c2.get(Calendar.DATE);
         if(y1==y2&&m1==m2&&d1==d2)
         {
          return true;
         }
     }
     else
     {
         if(time1==null&&time2==null)
         {
            return true;
         }
     }
     return false;
    }
    /**
     * 计算日期周几
     * @Title: getWeekDay 
     * @param @param dateStr
     * @param @return    
     * @return String    
     * @throws
     */
    public static String getWeekDay(String dateStr)
    {
    	String[] week = {"周日","周一","周二","周三","周四","周五","周六"};
    	Date date = convertToDate(dateStr,"yyyy-MM-dd");
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int weekno = CommNumTools.getFormatInt(cal.get(Calendar.DAY_OF_WEEK))-1;
    	if(weekno<0){
    		weekno = 0;
    	}
    	return week[weekno];
    }
    /**
     * 计算日期周几
     * @Title: getWeekDay 
     * @param @param dateStr
     * @param @return    
     * @return String    
     * @throws
     */
    public static String getWeekDay(String dateStr, String format)
    {
    	String[] week = {"周日","周一","周二","周三","周四","周五","周六"};
    	Date date = convertToDate(dateStr, format);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int weekno = CommNumTools.getFormatInt(cal.get(Calendar.DAY_OF_WEEK))-1;
    	if(weekno<0){
    		weekno = 0;
    	}
    	return week[weekno];
    }

    /**
     * 计算月开始时间
     * @Title: getWeekDay
     * @throws
     */
    public static String getMonthStartTime(String year, String month)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(month).append("-01 00:00:00");
        return sb.toString();
    }
    /**
     * 计算月结束时间
     * @Title: getWeekDay
     * @throws
     */
    public static String getMonthEndTime(String year, String month)
    {
        StringBuilder sb = new StringBuilder();
        int monthInt = Integer.parseInt(month);
        sb.append(year).append("-").append(month);
        if (monthInt == 1 || monthInt == 3 || monthInt == 5|| monthInt == 7|| monthInt == 8|| monthInt == 10|| monthInt == 12){
            sb.append("-31 23:59:59");
        }else if (monthInt == 2){
            sb.append("-28 23:59:59");
        }else{
            sb.append("-30 23:59:59");
        }
        return sb.toString();
    }

    public static String getMonthFormat(int month){
        String monthStr = month+"";
        if (month < 10){
            monthStr = "0"+month;
        }
        return monthStr;
    }

    public static String getDateFormat(int date){
        String dateStr = date+"";
        if (date < 10){
            dateStr = "0"+date;
        }
        return dateStr;
    }
//    /**
//     * 获取农历日期
//     * @Title: getLunarDay
//     * @param @param dateStr
//     * @param @return
//     * @return String
//     * @throws
//     */
//    public static String getLunarDay(String year, String month, String day)
//    {
//		LunarCalendar lunar =  new LunarCalendar(year, month, day);
//		return lunar.getLunarYear() + lunar.getLunarAnimal() + "年" + lunar.getLunarMonth() + "月" + lunar.getLunarDate();
//    }
//    /**
//     * 获取农历日期,没有年份
//     * @param month
//     * @param day
//     * @return
//     */
//    public static String getLunarWithoutYear(String year, String month, String day)
//    {
//		LunarCalendar lunar =  new LunarCalendar(year, month, day);
//		return lunar.getLunarMonth() + "月" + lunar.getLunarDate();
//    }
}
