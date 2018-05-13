package edu.tongji.sse.qyd.costAndEffortType;

import edu.tongji.sse.qyd.costAndEffortType.EffortType;

/**
 * Created by qyd on 2018/5/13.
 */
public class EffortTypeSet {
    public EffortType errorType = new EffortType(-1, "errorType");

    public EffortType traditionalCode = new EffortType(0, "traditionalCode");

    public EffortType reusableCode = new EffortType(1, "reusableCode");

    public EffortType autoScript = new EffortType(2, "autoScript");

    public EffortType reportDefect = new EffortType(3, "reportDefect");

    public EffortType repairDefect = new EffortType(4, "repairDefect");

    public EffortType[] effortTypeList = new EffortType[]{
            this.traditionalCode,
            this.reusableCode,
            this.autoScript,
            this.reportDefect,
            this.repairDefect
    };
}
