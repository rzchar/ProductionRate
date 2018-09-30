package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;

import java.io.File;
import java.util.List;

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

    @Override
    protected IssueClosedSinglePeriodAnalyzer getSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        return new IssueClosedSinglePeriodAnalyzer(datePeriod, urls);
    }
}
