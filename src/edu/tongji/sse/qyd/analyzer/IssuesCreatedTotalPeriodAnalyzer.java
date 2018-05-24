package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;

import java.io.File;

/**
 * Created by qyd on 2018/5/24.
 */
public class IssuesCreatedTotalPeriodAnalyzer extends BasicTotalPeriodAnalyzer<IssueCreatedSinglePeriodAnalyzer> {
    @Override
    protected String getFileFullName(String groupFolderPath, DatePeriod datePeriod) {
        return groupFolderPath + File.separator + datePeriod.getSinceUntilFileName("issuesList",".txt");
    }

    @Override
    protected Class<IssueCreatedSinglePeriodAnalyzer> getSingleEntityType() {
        return IssueCreatedSinglePeriodAnalyzer.class;
    }
}
