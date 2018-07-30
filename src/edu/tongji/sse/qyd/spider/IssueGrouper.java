package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Util;
import edu.tongji.sse.qyd.model.IssueInfo;

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
        List<IssueInfo> issueInfoList = IssueSpider.getInstance().getAllEntity(issueListName);
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

    public void makeListAndWriteToFile(String folderPath, List<DatePeriod> datePeriodList) {

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Map<DatePeriod, List<IssueInfo>> dividedIssuesMap = getIssueGroupByTime(datePeriodList);
        for (DatePeriod datePeriod : dividedIssuesMap.keySet()) {
            String fileName = datePeriod.getSinceUntilFileName("issuesList", ".txt");
            File f = new File(folderPath + File.separator + fileName);
            if (f.exists()) {
                f.delete();
            }
            List<IssueInfo> issueInfoList = dividedIssuesMap.get(datePeriod);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                for (IssueInfo issueInfo : issueInfoList) {
                    bw.write(issueInfo.getUrl() + "\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
