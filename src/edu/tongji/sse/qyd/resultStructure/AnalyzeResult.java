package edu.tongji.sse.qyd.resultStructure;

import edu.tongji.sse.qyd.Util.Util;
import edu.tongji.sse.qyd.resultStructure.cost.CostTypeSet;
import edu.tongji.sse.qyd.resultStructure.effort.EffortTypeSet;
import edu.tongji.sse.qyd.resultStructure.info.InfoSet;

/**
 * Created by qyd on 2018/5/21.
 */
public class AnalyzeResult {
    private CostTypeSet costTypeSet;
    private EffortTypeSet effortTypeSet;
    private InfoSet infoSet;

    public AnalyzeResult(CostTypeSet costTypeSet, EffortTypeSet effortTypeSet) {
        this.costTypeSet = costTypeSet;
        this.effortTypeSet = effortTypeSet;
        this.infoSet = new InfoSet();
    }

    public AnalyzeResult(CostTypeSet costTypeSet, EffortTypeSet effortTypeSet, InfoSet infoSet) {
        this.costTypeSet = costTypeSet;
        this.effortTypeSet = effortTypeSet;
        this.infoSet = infoSet;
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

    public InfoSet getInfoSet() {
        return infoSet;
    }

    public void setInfoSet(InfoSet infoSet) {
        this.infoSet = infoSet;
    }

    public void summary() {
        Util.log(this.getClass(), "");
        this.costTypeSet.summary();
        this.effortTypeSet.summary();
    }

}
