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

    public static Map<DatePeriod, List<IssueInfo>> getIssueGroupByTime(List<DatePeriod> datePeriodList, GroupBy groupBy) {
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

    public static void makeList(GroupBy groupBy, String folderPath, List<DatePeriod> datePeriodList) {

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        Map<DatePeriod, List<IssueInfo>> devidedIssuesMap = getIssueGroupByTime(datePeriodList, groupBy);
        for (DatePeriod datePeriod : devidedIssuesMap.keySet()) {
            String fileName = datePeriod.getSinceUntilFileName("issuesList", ".txt");
            File f = new File(folderPath + File.separator + fileName);
            if (f.exists()) {
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

    static public void main(String[] args) {
        String createdFolderPath = Path.middleDataPath + File.separator + "issueGroupsByCreatedAt";
        String closedFolderPath = Path.middleDataPath + File.separator + "issueGroupsByClosedAt";
        IssueGroupSpider.makeList(GroupBy.createdTime, createdFolderPath + File.separator + "whole", DatePeriod.getEclipseCheWholeLifeTime());
        IssueGroupSpider.makeList(GroupBy.closedTime, closedFolderPath + File.separator + "whole", DatePeriod.getEclipseCheWholeLifeTime());
//        IssueGroupSpider.makeList(GroupBy.createdTime, createdFolderPath + File.separator + "V4", DatePeriod.getEclipseCheV4LifeTime());
//        IssueGroupSpider.makeList(GroupBy.closedTime, closedFolderPath + File.separator + "V4", DatePeriod.getEclipseCheV4LifeTime());
//        IssueGroupSpider.makeList(GroupBy.createdTime, createdFolderPath + File.separator + "V5", DatePeriod.getEclipseCheV5LifeTime());
//        IssueGroupSpider.makeList(GroupBy.closedTime, closedFolderPath + File.separator + "V5", DatePeriod.getEclipseCheV5LifeTime());
       IssueGroupSpider.makeList(GroupBy.createdTime, createdFolderPath + File.separator + "V6", DatePeriod.getEclipseCheV6LifeTime());
        IssueGroupSpider.makeList(GroupBy.closedTime, closedFolderPath + File.separator + "V6", DatePeriod.getEclipseCheV6LifeTime());
    }

}
