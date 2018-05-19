package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Path;

import java.io.File;

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
}
