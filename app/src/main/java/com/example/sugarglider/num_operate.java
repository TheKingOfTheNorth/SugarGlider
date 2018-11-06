package com.example.sugarglider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class num_operate {
    public static String getToday(){
        Calendar c = Calendar.getInstance();
        int  mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String beforedate = mYear+"-"+(mMonth+1)+"-"+mDay;
        return beforedate;
    }
    public static String getTodaytwo(){
        Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String beforedate =(mMonth+1)+"-"+mDay;
        return beforedate;
    }
    public static String getTodayyear(){
        Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.YEAR);
        String yy = mMonth+"";
        return yy;
    }
    public static String getTodaymonth(){
        Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH);
        String mm = (mMonth+1)+"";
        return mm;
    }
    public static String getTodayday(){
        Calendar c = Calendar.getInstance();
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String dd = mDay+"";
        return dd;
    }

    public static long dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();

            day = diff / nd;// 计算差多少天
            if (day>=1) {
                return day;
            }else {
                if (day==0) {
                    return 0;
                }else {
                    return day;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
