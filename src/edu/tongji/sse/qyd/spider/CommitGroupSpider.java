package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;
import edu.tongji.sse.qyd.Util.Util;

import java.io.File;
import java.util.Date;

/**
 * Created by qyd on 2018/5/6.
 */
public class CommitGroupSpider {
    static private class WeekCommitListSpider extends CommitListSpider {
        public WeekCommitListSpider(String sinceUntil) {
            super();
            this.startURL = URLOfBasicAPI.commits + "?" + sinceUntil;
            commitListFileName = Path.middleDataPath + File.separator + "commitGroups"
                    + File.separator + "byWeek" + File.separator + "commitList"
                    + sinceUntil.replaceAll("[-=:&]","") + ".txt";
            makeNewEmptyFile();
        }
    }

    public static void main(String[] a){
        //System.out.println("since=2018-01-01T00:00:00Z&until=2018-01-07T23:59:59Z".replaceAll("[-=:&]",""));

        //WeekCommitListSpider weekCommitListSpider = new WeekCommitListSpider("since=2018-01-01T00:00:00Z&until=2018-01-07T23:59:59Z");
        //weekCommitListSpider.getListToFile();
        Date nowTime=new Date();
        System.out.println(Util.getISO8601Timestamp(nowTime));
    }
}
