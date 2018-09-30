package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.model.Project;
import edu.tongji.sse.qyd.util.DatePeriod;

import java.io.File;
import java.util.List;

/**
 * Created by qyd on 2018/5/17.
 */
public class CommitTotalPeriodAnalyzer extends BasicTotalPeriodAnalyzer<CommitSinglePeriodAnalyzer> {

    @Override
    protected String getFileFullName(String groupFolderPath, DatePeriod datePeriod) {
        return groupFolderPath + File.separator + datePeriod.getSinceUntilFileName("commitList", ".txt");
    }

    @Override
    protected Class<CommitSinglePeriodAnalyzer> getSingleEntityType() {
        return CommitSinglePeriodAnalyzer.class;
    }

    @Override
    protected CommitSinglePeriodAnalyzer getSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        CommitSinglePeriodAnalyzer cspa = new CommitSinglePeriodAnalyzer(datePeriod, urls);
        Project.getInstance().setFilePatternsTo(cspa);
        return cspa;
    }
}
