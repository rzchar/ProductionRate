package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.Util;
import edu.tongji.sse.qyd.gitIssue.IssueInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In fact, this is a issue cataloger instead of a spider
 * <p>
 * Created by qyd on 2018/5/14.
 */
public class IssueGroupSpider {
    public enum GroupBy {createdTime, closedTime}

    private static String folderPath = Path.middleDataPath + File.separator + "issueGroups" + File.separator + "GroupByCreatedAt";

    public static Map<DatePeriod, List<IssueInfo>> getIssueGroupByTime(List<DatePeriod> datePeriodList, GroupBy groupBy) {
        File folder = new File(folderPath);
        if(!folder.exists()){
            folder.mkdir();
        }
        List<IssueInfo> issueInfoList = IssuesSpider.getInstance().getAllEntity();
        Map<DatePeriod, List<IssueInfo>> issuesByCreatedTime = new HashMap<>();
        for (IssueInfo issue : issueInfoList) {
            Util.log(IssueGroupSpider.class, Util.getISO8601Timestamp(issue.getCreatedAt()));
            for (DatePeriod datePeriod : datePeriodList) {
                if (
                        (datePeriod.contain(issue.getCreatedAt()) && groupBy.equals(GroupBy.createdTime)) ||
                                (datePeriod.contain(issue.getClosedAt()) && groupBy.equals(GroupBy.closedTime))
                        ) {
                    if (!issuesByCreatedTime.keySet().contains(datePeriod)) {
                        issuesByCreatedTime.put(datePeriod, new ArrayList<>());
                    }
                    issuesByCreatedTime.get(datePeriod).add(issue);
                }
            }
        }
        return issuesByCreatedTime;
    }

    static public void main(String[] args) {

        List<DatePeriod> datePeriodList = DatePeriod.getEclipseCheLifeTime();
        Map<DatePeriod, List<IssueInfo>> devidedIssuesMap = getIssueGroupByTime(datePeriodList, GroupBy.createdTime);
        for (DatePeriod datePeriod : devidedIssuesMap.keySet()) {
            String fileName = datePeriod.getSinceUntilFileName("issuesList", ".txt");
            File f = new File(folderPath + File.separator + fileName);
            if(f.exists()){
                f.delete();
            }
            List<IssueInfo> issueInfoList = devidedIssuesMap.get(datePeriod);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                for (IssueInfo issueInfo : issueInfoList) {
                    bw.write("https://api.github.com/repos/eclipse/che/issues/" + issueInfo.getNumber() + "\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
