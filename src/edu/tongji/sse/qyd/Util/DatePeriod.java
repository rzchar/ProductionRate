package edu.tongji.sse.qyd.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/15.
 */
public class DatePeriod {

    public static final TimeZone utc0 = TimeZone.getTimeZone("UTC");

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public DatePeriod(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public boolean equals(DatePeriod datePeriod) {
        return this.start.equals(datePeriod.getStart()) && this.end.equals(datePeriod.getEnd());
    }

    public boolean contain(Date date) {
        if (date == null) {
            return false;
        }
        return date.after(this.start) && date.before(this.end);
    }

    public String getSinceUntilFileName(String prefix, String suffix) {
        return prefix + "since" + getISO8601Timestamp(this.start).replaceAll("[-=:&]", "")
                + "until" + getISO8601Timestamp(this.end).replaceAll("[-=:&]", "") + suffix;
    }

    private Date start;

    private Date end;

    public static List<DatePeriod> getPeriodList(Date start, Date end, int days) {
        List<DatePeriod> dd = new ArrayList<>();
        Date nextStart = getDateAfter(start, days);
        while (start.before(end)) {
            dd.add(new DatePeriod(start, nextStart));
            start = nextStart;
            nextStart = getDateAfter(start, days);
        }
        return dd;
    }

    /* Time Point
     *  4.0.0-RC4  2016-02-15T21:12:15Z
     *  5.0.0-M1  2016-09-14T15:50:16Z
     *  6.0.0-M1  2017-11-02T12:39:49Z
     */

    public static List<DatePeriod> getEclipseCheWholeLifeTime() {
        return getTimePeriodListByString("2015-03-27 00:00:00", "2018-05-30 00:00:00", 7);
    }

    public static List<DatePeriod> getEclipseCheV4LifeTime() {
        return getTimePeriodListByString("2016-06-15 00:00:00", "2016-09-14 00:00:00", 7);
    }

    public static List<DatePeriod> getEclipseCheV5LifeTime() {
        return getTimePeriodListByString("2016-09-14 00:00:00", "2017-11-02 00:00:00", 7);
    }

    public static List<DatePeriod> getEclipseCheV5E1LifeTime() {
        return getTimePeriodListByString("2016-09-14 00:00:00", "2017-11-02 00:00:00", 1);
    }

    public static List<DatePeriod> getEclipseCheV6LifeTime() {
        return getTimePeriodListByString("2017-11-02 00:00:00", "2018-05-30 00:00:00", 7);
    }

    public static List<DatePeriod> getTimePeriodListByString(String startTimeString, String endTimeString, int interval){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(utc0);
            Date adam = dateFormat.parse(startTimeString);
            Date start = adam;
            Date end = getDateAfter(start, interval);
            Date lilin = dateFormat.parse(endTimeString);
            return DatePeriod.getPeriodList(adam, lilin, interval);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

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
}
