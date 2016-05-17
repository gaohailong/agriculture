package com.sxau.agriculture.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具类
 *
 * @author 高海龙
 */
public class TimeUtil {

    //将秒转换为日期
    public static String format(long sec) {
        Date date = new Date(sec);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
