package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;
import edu.tongji.sse.qyd.Util.Util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qyd on 2018/5/6.
 */
public class CommitGroupSpider {
    static private class WeekCommitListSpider extends CommitListSpider {
        public WeekCommitListSpider(String sinceUntil) {
            this.startURL = URLOfBasicAPI.commits + "?" + sinceUntil;
            this.listFileName = Path.middleDataPath + File.separator + "commitGroups"
                    + File.separator + "byWeek" + File.separator + "commitList"
                    + sinceUntil.replaceAll("[-=:&]", "") + ".txt";
            makeNewEmptyFile();
        }
    }

    public static void main(String[] a) {
        //System.out.println("since=2018-01-01T00:00:00Z&until=2018-01-07T23:59:59Z".replaceAll("[-=:&]",""));

        //WeekCommitListSpider weekCommitListSpider = new WeekCommitListSpider("since=2018-01-01T00:00:00Z&until=2018-01-07T23:59:59Z");
        //weekCommitListSpider.getListToFile();
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(Util.utc0);
            Date adam = dateFormat.parse("2018-02-26 00:00:00");
            Date start= adam;
            Date end = Util.getDateAfter(start,7);
            Date lilin = dateFormat.parse("2018-04-30 00:00:00");
            while(start.before(lilin)){
                String time = "since="+Util.getISO8601Timestamp(start) +"&until=" + Util.getISO8601Timestamp(end);
                WeekCommitListSpider weekCommitListSpider = new WeekCommitListSpider(time);
                weekCommitListSpider.getListToFile();
                start = end;
                end =Util.getDateAfter(end,7);
            }
            System.out.println(Util.getISO8601Timestamp(Util.getDateAfter(adam, 7)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
