package edu.tongji.sse.qyd.costAndEffortType;

/**
 * Created by qyd on 2018/5/13.
 */
public class EffortType extends AbstractFileCommitType {
    public EffortType(int num, String typeString) {
        super(num, typeString);
    }

    public EffortType(int num, String typeString, int rate) {
        super(num, typeString, rate);
    }
}
