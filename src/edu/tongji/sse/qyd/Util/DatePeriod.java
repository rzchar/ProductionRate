package edu.tongji.sse.qyd.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qyd on 2018/5/15.
 */
public class DatePeriod {
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
        return prefix + "since" + Util.getISO8601Timestamp(this.start).replaceAll("[-=:&]", "")
                + "until" + Util.getISO8601Timestamp(this.end).replaceAll("[-=:&]", "") + suffix;
    }

    private Date start;

    private Date end;

    public static List<DatePeriod> getPeriodList(Date start, Date end, int days) {
        List<DatePeriod> dd = new ArrayList<>();
        Date nextStart = Util.getDateAfter(start, days);
        while (start.before(end)) {
            dd.add(new DatePeriod(start, nextStart));
            start = nextStart;
            nextStart = Util.getDateAfter(start, days);
        }
        return dd;
    }

    /* Time Point
     *  4.0.0-RC4  2016-02-15T21:12:15Z
     *  5.0.0-M1  2016-09-14T15:50:16Z
     *  6.0.0-M1  2017-11-02T12:39:49Z
     */

    public static List<DatePeriod> getEclipseCheWholeLifeTime() {
        return getTimePeriodListByString("2015-03-27 00:00:00", "2018-05-30 00:00:00");
    }

    public static List<DatePeriod> getEclipseCheV4LifeTime() {
        return getTimePeriodListByString("2016-06-15 00:00:00", "2016-09-14 00:00:00");
    }

    public static List<DatePeriod> getEclipseCheV5LifeTime() {
        return getTimePeriodListByString("2016-09-14 00:00:00", "2017-11-02 00:00:00");
    }

    public static List<DatePeriod> getEclipseCheV6LifeTime() {
        return getTimePeriodListByString("2017-11-02 00:00:00", "2018-05-30 00:00:00");
    }

    public static List<DatePeriod> getTimePeriodListByString(String startTimeString, String endTimeString){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(Util.utc0);
            Date adam = dateFormat.parse(startTimeString);
            Date start = adam;
            Date end = Util.getDateAfter(start, 7);
            Date lilin = dateFormat.parse(endTimeString);
            return DatePeriod.getPeriodList(adam, lilin, 7);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
