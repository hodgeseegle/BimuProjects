package com.can.bimuprojects.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by can on 2017/4/12.
 */
public class DateUtils {


    /**
     * 将时间戳转化为卡片上显示的时间，注意:后台传来的时间以秒为单位
     * @param timestamp
     * @return
     */
    public static String timestampToString(long timestamp) {
        timestamp = timestamp*1000; //后台传来的时间是以秒为单位的
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long currentMills = currentDate.getTime();
        int y = calendar.get(Calendar.YEAR);
        long interval = currentMills - timestamp;
        long lastYear = calendar.getTimeInMillis();

        if (interval >= 0 && interval <= 60 * 1000) {
            return "刚刚";
        }
        if (interval > 60 * 1000 && interval <= 60 * 60 * 1000) {
            return interval / 1000 / 60 + "分钟前";
        }
        if (interval > 60 * 60 * 1000 && interval <= 24 * 60 * 60 * 1000) {
            return interval / 60 / 60 / 1000 + "小时前";
        }
        Date time = new Date(timestamp);
        if (interval > 24 * 60 * 60 * 1000 && timestamp > lastYear) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            return sdf.format(time);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            return sdf.format(time);
        }

    }

    public static String timestampToString(String timestamp) {
        long stamp = 0;
        try{
             stamp= Long.valueOf(timestamp);
        }catch(NumberFormatException e){
        }
        return timestampToString(stamp);
    }
}
