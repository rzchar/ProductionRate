package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Util;
import edu.tongji.sse.qyd.costAndEffortType.CostTypeSet;
import edu.tongji.sse.qyd.costAndEffortType.EffortTypeSet;

import java.util.Date;
import java.util.List;

/**
 * Created by qyd on 2018/5/17.
 */
public abstract class BasicSinglePeriodAnalyzer {
    protected Date since;
    protected Date until;
    protected List<String> urls = null;
    protected CostTypeSet costTypeSet = new CostTypeSet();
    protected EffortTypeSet effortTypeSet = new EffortTypeSet();

    public BasicSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        this.until = datePeriod.getEnd();
        this.since = datePeriod.getStart();
        this.urls = urls;
    }

    public void summary() {
        Util.log(this.getClass(), "summary at " + Util.getISO8601Timestamp(since) + "=>" + Util.getISO8601Timestamp(until));
        this.costTypeSet.summary();
        this.effortTypeSet.summary();
    }

    public abstract AnalyzeResult statistic();
}
