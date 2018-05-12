package edu.tongji.sse.qyd.analyzer;

/**
 * Created by qyd on 2018/5/13.
 */
public class EffortType {
    String typeString;

    int num;

    int rate;

    public EffortType(int num, String typeString) {
        this.num = num;
        this.typeString = typeString;
        this.rate = 1;
    }

    public EffortType(int num, String typeString,int rate) {
        this.num = num;
        this.typeString = typeString;
        this.rate = rate;
    }

    public int  getRate(){
        return this.rate;
    }

    public int getNum() {
        return this.num;
    }

    @Override
    public String toString() {
        return typeString;
    }

    static public EffortType errorType = new EffortType(-1,"errorType");

    static public EffortType traditionalCode = new EffortType(0,"traditionalCode");

    static public EffortType reusableCode = new EffortType(1,"reusableCode");

    static public EffortType autoScript = new EffortType(2,"autoScript");

    static public EffortType reportDefect = new EffortType(3,"reportDefect");

    static public EffortType repairDefect = new EffortType(4,"repairDefect");
}
