package edu.tongji.sse.qyd.costAndEffortType;

/**
 * Created by qyd on 2018/5/13.
 */
public class EffortTypeSet {
    public EffortType errorTypeEffort = new EffortType(-1, "errorTypeEffort");

    public EffortType traditionalCodeEffort = new EffortType(0, "traditionalCodeEffort");

    public EffortType reusableCodeEffort = new EffortType(1, "reusableCodeEffort");

    public EffortType autoScriptEffort = new EffortType(2, "autoScriptEffort");

    public EffortType reportDefectEffort = new EffortType(3, "reportDefectEffort");

    public EffortType repairDefectEffort = new EffortType(4, "repairDefectEffort");

    public EffortType[] effortTypeList = new EffortType[]{
            this.errorTypeEffort,
            this.traditionalCodeEffort,
            this.reusableCodeEffort,
            this.autoScriptEffort,
            this.reportDefectEffort,
            this.repairDefectEffort
    };

    public void clear() {
        for (EffortType e : effortTypeList) {
            e.resetAll();
        }
    }

    public void summary() {
        double sum = 0d;
        for (EffortType e : effortTypeList) {
            sum += e.getAddLineSum();
            System.out.print(e.getTypeString() + ":" + e.summary() + ";   ");
        }
        System.out.println();
        System.out.println("total:" + sum);
    }
}
