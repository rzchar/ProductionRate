package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;

import java.util.List;

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

    @Override
    protected OtherInfoSinglePeriodAnalyzer getSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        return new OtherInfoSinglePeriodAnalyzer(datePeriod, urls);
    }
}
