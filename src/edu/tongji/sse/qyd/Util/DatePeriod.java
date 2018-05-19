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

    public static List<DatePeriod> getEclipseCheLifeTime() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(Util.utc0);
            Date adam = dateFormat.parse("2015-03-27 00:00:00");
            Date start = adam;
            Date end = Util.getDateAfter(start, 7);
            Date lilin = dateFormat.parse("2018-04-30 00:00:00");
            return DatePeriod.getPeriodList(adam, lilin, 7);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
