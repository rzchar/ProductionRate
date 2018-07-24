package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;

import java.io.File;

/**
 * Created by qyd on 2018/5/24.
 */
public class IssuesClosedTotalPeriodAnalyzer extends BasicTotalPeriodAnalyzer<IssueClosedSinglePeriodAnalyzer> {
    @Override
    protected String getFileFullName(String groupFolderPath, DatePeriod datePeriod) {
        return groupFolderPath + File.separator + datePeriod.getSinceUntilFileName("issuesList",".txt");
    }

    @Override
    protected Class<IssueClosedSinglePeriodAnalyzer> getSingleEntityType() {
        return IssueClosedSinglePeriodAnalyzer.class;
    }
}
