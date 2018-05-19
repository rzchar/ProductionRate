package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;
import edu.tongji.sse.qyd.Util.Util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by qyd on 2018/5/6.
 */
public class CommitGroupSpider {
    public static void main(String[] a) {
        //System.out.println("since=2018-01-01T00:00:00Z&until=2018-01-07T23:59:59Z".replaceAll("[-=:&]",""));

        //WeekCommitListSpider weekCommitListSpider = new WeekCommitListSpider("since=2018-01-01T00:00:00Z&until=2018-01-07T23:59:59Z");
        //weekCommitListSpider.getListToFile();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(Util.utc0);
        List<DatePeriod> datePeriodList = DatePeriod.getEclipseCheLifeTime();
        for (DatePeriod dd : datePeriodList) {
            String time = "since=" + Util.getISO8601Timestamp(dd.getStart())
                    + "&until=" + Util.getISO8601Timestamp(dd.getEnd());
            System.out.println(time);
            WeekCommitListSpider weekCommitListSpider = new WeekCommitListSpider(time);
            weekCommitListSpider.getListToFile();
        }
//            while (start.before(lilin)) {
//                String time = "since=" + Util.getISO8601Timestamp(start) + "&until=" + Util.getISO8601Timestamp(end);
//                start = end;
//                end = Util.getDateAfter(end, 7);
//            }
        //System.out.println(Util.getISO8601Timestamp(Util.getDateAfter(adam, 7)));


    }

    static private class WeekCommitListSpider extends CommitListSpider {
        public WeekCommitListSpider(String sinceUntil) {
            this.startURL = URLOfBasicAPI.commits + "?" + sinceUntil;
            this.listFileName = Path.middleDataPath + File.separator + "commitGroups"
                    + File.separator + "byWeek" + File.separator + "commitList"
                    + sinceUntil.replaceAll("[-=:&]", "") + ".txt";
            makeNewEmptyFile();
        }
    }
}
