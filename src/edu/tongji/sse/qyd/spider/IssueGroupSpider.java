package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Util;
import edu.tongji.sse.qyd.gitIssue.IssueInfo;

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
            System.out.println(Util.getISO8601Timestamp(issue.getCreatedAt()));
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
        getIssueGroupByTime(datePeriodList,GroupBy.createdTime);
    }

}
