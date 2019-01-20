package com.myssm.util;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @title :日期转换工具
 * @description :包含常用日期格式转换操作
 * @author: 
 * @data: 
 */
public class DateUtil {

    private static Calendar mycd = Calendar.getInstance();
    private static int year = 0;
    private static int month = 0;
    private static int date = 0;
    private static int hour = 0;
    private static int minute = 0;
    private static int second = 0;

    /** 设置系统当前日期 * */
    public DateUtil() {
        setdate();
    }

    /** 设置系统时间 * */
    private static void setdate() {
        year = mycd.get(Calendar.YEAR);
        month = mycd.get(Calendar.MONTH) + 1;
        date = mycd.get(Calendar.DATE);
        hour = mycd.get(Calendar.HOUR);
        minute = mycd.get(Calendar.MINUTE);
        second = mycd.get(Calendar.SECOND);
    }

    /** 得到系统当前日期及时间 格式为 yyyy-MM-dd HH:mm:ss * */
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    public static String getFileName() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS"); //精确到毫秒
        return  fmt.format(new Date());
    }

    /** 得到long型的日期值 * */
    public static Time getSqlTime() {
        return new Time(getTime());
    }

    /** 获取系统当前时间 * */
    public static long getTime() {
        java.util.Date dt = new java.util.Date();
        return dt.getTime();
    }

    /** 获取系统当前时间 * */
    public static Date getJavaDate() {
        return new java.util.Date();
    }

    /** 获取系统当前日期 得到的日期格式如：2004-10-09 * */
    public static java.sql.Date getSqlDate() {
        return new java.sql.Date(getTime());
    }

    /** 取得Timestamp类型时间 * */
    public static Timestamp getTimestamp() {
        return new Timestamp(getTime());
    }

    /** 得到Calendar对象 * */
    public static Calendar getCD() {
        Calendar mycd = Calendar.getInstance();
        return mycd;
    }

    /** 得到时间 * */
    public static String getAll(String sStr) {
        Calendar mycd = Calendar.getInstance();
        return mycd.get(Calendar.YEAR) + sStr + (mycd.get(Calendar.MONTH) + 1)
                + sStr + mycd.get(Calendar.DATE);
    }

    /** 得到日期,以-为分割符 * */
    public static String getAll() {
        return getAll("-");
    }

    /** 得到系统当前年 * */
    public static int getYear() {
        Calendar mycd = Calendar.getInstance();
        return mycd.get(Calendar.YEAR);
    }

    /** 得到系统当前月 * */
    public static int getMonth() {
        Calendar mycd = Calendar.getInstance();
        return mycd.get(Calendar.MONTH) + 1;
    }

    /** 得到系统当前日 * */
    public static int getDate() {
        Calendar mycd = Calendar.getInstance();
        return mycd.get(Calendar.DATE);
    }

    /** 得到系统年 * */
    public static int getAddYear() {
        return mycd.get(Calendar.YEAR);
    }

    /** 得到系统月 * */
    public static int getAddMonth() {
        return mycd.get(Calendar.MONTH) + 1;
    }

    /** 得到系统日 * */
    public static int getAddDate() {
        return mycd.get(Calendar.DATE);
    }

    /** 得到日期格式为yyyy-mm-dd * */
    public static String getMiddle() {
        return getMiddle("-");
    }
    
    /** 得到日期格式为YYYY $sStr MM 其中sStr为分割字符 * */
    public static String getMiddleYM(String sStr) {
        year = mycd.get(Calendar.YEAR);
        month = mycd.get(Calendar.MONTH) + 1;
        String re = "" + String.valueOf(year);
        if (month < 10)
            re += sStr + "0" + String.valueOf(month);
        else
            re += sStr + String.valueOf(month);
        return re;
    }

    /** 得到日期格式为YYYY $sStr MM $sStr DD其中sStr为分割字符 * */
    public static String getMiddle(String sStr) {
        year = mycd.get(Calendar.YEAR);
        month = mycd.get(Calendar.MONTH) + 1;
        date = mycd.get(Calendar.DATE);
        String re = "" + String.valueOf(year);
        if (month < 10)
            re += sStr + "0" + String.valueOf(month);
        else
            re += sStr + String.valueOf(month);
        if (date < 10)
            re += sStr + "0" + String.valueOf(date);
        else
            re += sStr + String.valueOf(date);
        return re;
    }

    /** 得到日期格式为 YYYY $sStr MM $sStr DD $sStr hh:mm:ss其中sStr为分割字符 * */
    public static String getTimeStr(String sStr) {

        Calendar mycd = Calendar.getInstance();
        String re = "" + mycd.get(Calendar.YEAR);
        if (mycd.get(Calendar.MONTH) + 1 < 10)
            re += sStr + "0" + String.valueOf(mycd.get(Calendar.MONTH) + 1);
        else
            re += sStr + String.valueOf(mycd.get(Calendar.MONTH) + 1);
        if (mycd.get(Calendar.DATE) < 10)
            re += sStr + "0" + String.valueOf(mycd.get(Calendar.DATE));
        else
            re += sStr + String.valueOf(mycd.get(Calendar.DATE));

        if (mycd.get(Calendar.HOUR) < 10)
            re += " " + "0" + String.valueOf(mycd.get(Calendar.HOUR));
        else
            re += " " + String.valueOf(mycd.get(Calendar.HOUR));
        if (mycd.get(Calendar.MINUTE) < 10)
            re += ":0" + String.valueOf(mycd.get(Calendar.MINUTE));
        else
            re += ":" + String.valueOf(mycd.get(Calendar.MINUTE));
        if (mycd.get(Calendar.SECOND) < 10)
            re += ":0" + String.valueOf(mycd.get(Calendar.SECOND));
        else
            re += ":" + String.valueOf(mycd.get(Calendar.SECOND));
        return re;
    }

    /** 通过给定的年、月、日 设置Calendar对象 * */
    public DateUtil(String Year, String Month, String Day) {
        try {

            mycd.set(Integer.parseInt(Year), Integer.parseInt(Month) - 1,
                    Integer.parseInt(Day));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public DateUtil(Date nd) {
        mycd.setTime(nd);
        this.setdate();
    }

    /** 通过给定的年、月、日 设置Calendar对象 * */
    public DateUtil(int inty, int intm, int intd) {
        mycd.set(inty, intm - 1, intd);
        this.setdate();
    }

    /** 日期加nday天后，从新设置系统当前时间 * */
    public void addDay(int nday) {
        mycd.set(year, month - 1, date + nday);
        setdate();
    }

    /** 日期加nday天后，从新设置系统当前时间 * */
    public void addDay(String nday) {
        addDay(Integer.parseInt(nday));
    }

    /** 日期加nmonth月后，从新设置系统当前时间 * */
    public void addMonth(int nmonth) {
        mycd.set(year, month + nmonth - 1, date);
        this.setdate();
    }

    /** 日期加nmonth月后，从新设置系统当前时间 * */
    public void addMonth(String nmonth) {
        addMonth(Integer.parseInt(nmonth));
    }

    /** 日期加nyear年后，从新设置系统当前时间 * */
    public void addYear(int nyear) {
        mycd.set(year + nyear, month - 1, date);
        this.setdate();
    }

    /** 日期加nyear年后，从新设置系统当前时间 * */
    public void addYear(String nyear) {
        addYear(Integer.parseInt(nyear));
    }

    /** 取得两个日期的相隔天数 * */
    public static int dayDiff(DateUtil a, DateUtil b) {
        int n = 0;
        long temptime = b.getTime() - a.getTime();
        temptime /= 24 * 3600 * 1000;
        return Integer.parseInt(String.valueOf(temptime));
    }

    /** 取得两个日期的相隔的月数 * */
    public static int yearDiff(DateUtil a, DateUtil b) {
        return (b.getYear() - a.getYear());
    }

    /** 取得两个日期的相隔的月数 * */
    public static int monthDiff(DateUtil a, DateUtil b) {
        int n = 0;
        n = yearDiff(a, b);
        n = n * 12 + (b.getMonth() - a.getMonth());
        return n;
    }

    /** 取得两个日期的相隔天数 * */
    public static int getDays(Date sd, Date ed) {
        return (int)((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000));
    }

    /***************************************************************************
     * 取得yyyymm,参数一：yyyymm,参数二：number 得到减去月份的日期
     **************************************************************************/
    public static String getYearMonth(String str, int num) {
        Calendar mycdar = Calendar.getInstance();
        int yearSub = Integer.parseInt(str.substring(0, 4));
        int monthSub = Integer.parseInt(str.substring(4, 6)) - 1;
        mycdar.set(yearSub, monthSub - num, 1);

        // 月处理
        String monthStr = "";
        monthSub = mycdar.get(mycdar.MONTH) + 1;
        if (monthSub < 10)
            monthStr = "0" + String.valueOf(monthSub);
        else
            monthStr = String.valueOf(monthSub);

        return String.valueOf(mycdar.get(mycdar.YEAR)) + monthStr;
    }

    public static String getTime(String s) {
        if (s == null || s.equals(""))
            return "";
        String s1 = "";
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
            s1 = simpledateformat.format(Calendar.getInstance().getTime());
        } catch (Exception exception) {
            System.out.println(Calendar.getInstance().toString()
                    + "cannot format time [function:getTime(String)]");
            exception.printStackTrace();
        }
        return s1;
    }
    
    /** 将String 转换操作，将sDt替换为Timestamp型的日期型 **/
    public static java.sql.Timestamp getDateTime ( String sDt )
    {
            try
            {
                    return java.sql.Timestamp.valueOf (sDt) ; 
            } catch (IllegalArgumentException iae)
            {
                    sDt = sDt + " 00:00:00" ;
                    try
                    {
                            return java.sql.Timestamp.valueOf ( sDt ) ;
                    } catch (Exception e)
                    {
                            return null ;
                    }
            }
    }
	/**
	 * String转成java.util.Date对象
	 * 
	 * @author zhangg
	 * @param strDate
	 *            时间字符串
	 * @param pattern
	 *            规则 例如："yyyy-MM-dd hh:mm:ss"
	 * @return java.util.Date Date对象
	 * @see java.text.SimpleDateFormat#parse(String)
	 * @throws ParseException
	 */
	public static java.util.Date string2Date(String strDate, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(strDate);
	}
	/**
	 * java.util.Date转成String对象
	 * 
	 * @author zhangg
	 * @param date
	 *            java.util.Date对象
	 * @param pattern
	 *            规则 例如："yyyy-MM-dd hh:mm:ss"
	 * @see java.text.SimpleDateFormat#format(Date)
	 * @return String 转换后的时间
	 */
	public static String date2String(java.util.Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	/**
	 * String转成java.sql.Date对象
	 * 
	 * @author zhangg
	 * @param strDate
	 *            时间字符串
	 * @param pattern
	 *            规则 例如："yyyy-MM-dd hh:mm:ss"
	 * @return java.sql.Date Date对象
	 * @see java.text.SimpleDateFormat#parse(String)
	 * @throws ParseException
	 */
	public static java.sql.Date string2SqlDate(String strDate, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date date = sdf.parse(strDate);
		return new java.sql.Date(date.getTime());
	}
	/**
	 * java.sql.Date对象转成String
	 * 
	 * @author zhangg
	 * @param date
	 *            java.sql.Date对象
	 * @param pattern
	 *            规则 例如："yyyy-MM-dd hh:mm:ss"
	 * @see java.text.SimpleDateFormat#format(Date)
	 * @return String 转换后的时间
	 */
	public static String sqlDate2String(java.sql.Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 获取返回类型为Timestamp的当前时间
	 * 
	 * @author 李银辉
	 * @return Timestamp 当前时间
	 */
	public static Timestamp getCurrentTime() {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		return t;
	}
	
	
	private static String [] CHN_NUM = {"零","一","二","三","四","五","六","七","八","九","十","十一","十二"};
	/**
	 * 显示中文年月
	 * @return String
	 * @author sw
	 * 2009-10-26
	 */
	public static String getCHNYearMonth() {
		
		StringBuilder str = new StringBuilder(20);
		String year = Calendar.getInstance().get(Calendar.YEAR) + "";
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;  
		
		for(int i: year.getBytes())
			str.append(CHN_NUM[i-48]);
		str.append("年");
		str.append(CHN_NUM[month]).append("月");
		
		return str.toString();
	}
	
	/**         

	* @函数名称：toDay                 
	* @功能描述： 获得格式化后的日期	                
	* @return： 格式化后的日期
	* @exception:    
	* @throws SQLException    
	*/
	public static String toDay() {
		GregorianCalendar thisday = new GregorianCalendar();
		Date d = thisday.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(d);
		return s;
	}
	/** 获取当前时间之前后者之后的日期   (传-1 获取前天的时间)
	 * @param day
	 * @return
	 */
	public static java.util.Date getOtherDate(int offset){
		Calendar c = Calendar.getInstance();		
		c.add(c.DATE, offset);
		java.util.Date date = c.getTime();
		return date;
	}
	
	public   static java.util.Date   add(java.util.Date date,int  amount){   
        java.util.Calendar   c=java.util.Calendar.getInstance();   
        c.setTime(date);   
        c.add(Calendar.DAY_OF_MONTH,amount);   
        return   c.getTime();   
}   
	/**
	 * 把字符串日期转换为日期对象
	 * @param date
	 * @return
	 */
	public static Date fomateDate(String date){
		Date d = null;
		if(date != null && date.trim().length() > 0){
			try {
				d =	DateFormat.getDateInstance().parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return d ;
	}

}
