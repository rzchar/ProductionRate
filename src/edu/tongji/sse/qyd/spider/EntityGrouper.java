package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.DatePeriod;

import java.util.List;
import java.util.Map;

/**
 * Created by qyd on 2018/7/17.
 */
public interface EntityGrouper <T>{
    Map<DatePeriod, List<T>> getIssueGroupByTime(List<DatePeriod> datePeriodList);

    void writeListToFile(String folderPath, List<DatePeriod> datePeriodList);
}
