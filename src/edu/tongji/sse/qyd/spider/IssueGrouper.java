package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Util;
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
public class IssueGrouper implements EntityGrouper<IssueInfo> {


    public enum GroupBy {createdTime, closedTime}

    private GroupBy groupBy;

    private String issueListName;

    public IssueGrouper(GroupBy groupBy, String issueListName) {
        this.groupBy = groupBy;
        this.issueListName = issueListName;
    }

    public Map<DatePeriod, List<IssueInfo>> getIssueGroupByTime(List<DatePeriod> datePeriodList) {
        List<IssueInfo> issueInfoList = IssuesSpider.getInstance().getAllEntity(issueListName);
        Map<DatePeriod, List<IssueInfo>> issuesByCreatedTime = new HashMap<>();
        for (IssueInfo issue : issueInfoList) {
            Util.log(IssueGrouper.class, DatePeriod.getISO8601Timestamp(issue.getCreatedAt()));
            for (DatePeriod datePeriod : datePeriodList) {
                if ((datePeriod.contain(issue.getCreatedAt()) && this.groupBy.equals(GroupBy.createdTime)) ||
                        (datePeriod.contain(issue.getClosedAt()) && this.groupBy.equals(GroupBy.closedTime))
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

    public void writeListToFile(String folderPath, List<DatePeriod> datePeriodList) {

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        Map<DatePeriod, List<IssueInfo>> devidedIssuesMap = getIssueGroupByTime(datePeriodList);
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
//        String createdFolderPath = Path.getMiddleDataPath() + File.separator + "issueGroupsByCreatedAt";
//        String closedFolderPath = Path.getMiddleDataPath() + File.separator + "issueGroupsByClosedAt";
//        IssueGrouper.writeListToFile(GroupBy.createdTime, createdFolderPath + File.separator + "whole", DatePeriod.getEclipseCheWholeLifeTime());
//        IssueGrouper.writeListToFile(GroupBy.closedTime, closedFolderPath + File.separator + "whole", DatePeriod.getEclipseCheWholeLifeTime());
//        IssueGrouper.writeListToFile(GroupBy.createdTime, createdFolderPath + File.separator + "V4", DatePeriod.getEclipseCheV4LifeTime());
//        IssueGrouper.writeListToFile(GroupBy.closedTime, closedFolderPath + File.separator + "V4", DatePeriod.getEclipseCheV4LifeTime());
//        IssueGrouper.writeListToFile(GroupBy.createdTime, createdFolderPath + File.separator + "V5", DatePeriod.getEclipseCheV5LifeTime());
//        IssueGrouper.writeListToFile(GroupBy.closedTime, closedFolderPath + File.separator + "V5", DatePeriod.getEclipseCheV5LifeTime());
//        IssueGrouper.writeListToFile(GroupBy.createdTime, createdFolderPath + File.separator + "V6", DatePeriod.getEclipseCheV6LifeTime());
//        IssueGrouper.writeListToFile(GroupBy.closedTime, closedFolderPath + File.separator + "V6", DatePeriod.getEclipseCheV6LifeTime());
//        IssueGrouper.writeListToFile(GroupBy.createdTime, createdFolderPath + File.separator + "V5.1", DatePeriod.getEclipseCheV5E1LifeTime(), "#issueListAll.txt");
//        IssueGrouper.writeListToFile(GroupBy.closedTime, closedFolderPath + File.separator + "V5.1", DatePeriod.getEclipseCheV5E1LifeTime(), "#issueListAll.txt");
    }

}
