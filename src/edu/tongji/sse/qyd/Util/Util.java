package edu.tongji.sse.qyd.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/7.
 */
public class Util {
    public static final TimeZone utc0 = TimeZone.getTimeZone("UTC");

    public static final DateFormat dateFormatISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static final DateFormat dateFormatFileName = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

    private static final Pattern commitGroupFileNamePattern = Pattern.compile("since([0-9TZ\\-:]*)until([0-9TZ\\-:]*)");

    public static String getISO8601Timestamp(Date date) {
        dateFormatISO8601.setTimeZone(utc0);
        String nowAsISO = dateFormatISO8601.format(date);
        return nowAsISO;
    }

    public static Date getDateFromISO8601(String time) {
        if (time == null) {
            return null;
        }
        //String simpleTime = time.replace("[^0-9TZ]","");
        try {
            dateFormatISO8601.setTimeZone(utc0);
            return dateFormatISO8601.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(utc0);
        calendar.setTime(d);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
        return calendar.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(utc0);
        calendar.setTime(d);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
        return calendar.getTime();
    }

    private static Date getDateFromString(String str, int group) {
        Matcher matcher = commitGroupFileNamePattern.matcher(str);
        if (matcher.find()) {
            try {
                return dateFormatFileName.parse(matcher.group(group));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date getSince(String str) {
        return getDateFromString(str, 1);
    }

    public static Date getUntil(String str) {
        return getDateFromString(str, 2);
    }

    public static void log(Class c, String message) {
        System.out.println("[" + c.getName() + "]" + message);
    }

}
