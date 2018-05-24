package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.Util;
import edu.tongji.sse.qyd.costAndEffortType.CostTypeSet;
import edu.tongji.sse.qyd.costAndEffortType.EffortTypeSet;

/**
 * Created by qyd on 2018/5/21.
 */
public class AnalyzeResult {
    private CostTypeSet costTypeSet;
    private EffortTypeSet effortTypeSet;

    public AnalyzeResult(CostTypeSet costTypeSet, EffortTypeSet effortTypeSet) {
        this.costTypeSet = costTypeSet;
        this.effortTypeSet = effortTypeSet;
    }

    public CostTypeSet getCostTypeSet() {
        return costTypeSet;
    }

    public void setCostTypeSet(CostTypeSet costTypeSet) {
        this.costTypeSet = costTypeSet;
    }

    public EffortTypeSet getEffortTypeSet() {
        return effortTypeSet;
    }

    public void setEffortTypeSet(EffortTypeSet effortTypeSet) {
        this.effortTypeSet = effortTypeSet;
    }

    public void summary() {
        Util.log(this.getClass(), "");
        this.costTypeSet.summary();
        this.effortTypeSet.summary();
    }

}
