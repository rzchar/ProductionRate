package edu.tongji.sse.qyd.costAndEffortType;

import edu.tongji.sse.qyd.gitCommit.GitCommitFileInfo;

/**
 * Created by qyd on 2018/5/13.
 */
public class AbstractFileCommitType {
    String typeString;
    int num;
    int rate;
    int addLineSum;
    int deleteLineSum;
    int changeLineSum;

    public AbstractFileCommitType() {
        this.addLineSum = 0;
    }

    public AbstractFileCommitType(int num, String typeString) {
        super();
        this.num = num;
        this.typeString = typeString;
        this.rate = 1;
        this.resetAll();
    }

    public AbstractFileCommitType(int num, String typeString, int rate) {
        super();
        this.num = num;
        this.typeString = typeString;
        this.rate = rate;
        this.resetAll();
    }

    public int getRate() {
        return this.rate;
    }

    public int getNum() {
        return this.num;
    }

    public String getTypeString() {
        return typeString;
    }

    public String summary() {
        return "add: " + this.addLineSum + ";  delete: " + this.deleteLineSum + ";  change: " + changeLineSum + "; ";
    }

    public void addAddLine(int add) {
        this.addLineSum += add;
    }

    public void addDeleteLine(int delete) {
        this.deleteLineSum += delete;
    }

    public void addChangeLine(int change) {
        this.changeLineSum += change;
    }

    public void resetAll() {
        this.addLineSum = 0;
        this.deleteLineSum = 0;
        this.changeLineSum = 0;
    }

    public void addCommitData(GitCommitFileInfo info) {
        this.addLineSum += info.getAdditionNum();
        this.deleteLineSum += info.getDeletionNum();
        this.changeLineSum += info.getChangeNum();
    }
}
