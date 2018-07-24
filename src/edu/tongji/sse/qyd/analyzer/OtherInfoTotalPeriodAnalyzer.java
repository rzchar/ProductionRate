package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;

/**
 * Created by qyd on 2018/6/18.
 */
public class OtherInfoTotalPeriodAnalyzer extends BasicTotalPeriodAnalyzer<OtherInfoSinglePeriodAnalyzer> {
    @Override
    protected String getFileFullName(String groupFolderPath, DatePeriod datePeriod) {
        return null;
    }

    @Override
    protected Class<OtherInfoSinglePeriodAnalyzer> getSingleEntityType() {
        return null;
    }
}
