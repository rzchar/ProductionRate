package edu.tongji.sse.qyd.analyzer;

/**
 * Created by qyd on 2018/5/13.
 */
public class CostType {
    String typeString;

    int num;

    int rate;

    public CostType(int num, String typeString) {
        this.num = num;
        this.typeString = typeString;
        this.rate = 1;
    }

    public CostType(int num, String typeString,int rate) {
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
    public static CostType errorType = new CostType(-1, "errorType");

    public static CostType traditionalCodeCost = new CostType(0, "traditionalCodeCost");

    public static CostType developingEnvironmentEstablish = new CostType(1, "developingEnvironmentEstablish");

    public static CostType fetchReusableResourceWithCrawler = new CostType(2, "fetchReusableResourceWithCrawler");

    public static CostType fetchReusableResourceWithVCS = new CostType(3, "fetchReusableResourceWithVCS");

    public static CostType autoCompile = new CostType(4, "autoCompile");

    public static CostType autoTest = new CostType(5, "autoTest");

    public static CostType runtimeEnvironmentEstablish = new CostType(6, "runtimeEnvironmentEstablish");

    public static CostType deployMidwareAndApplication = new CostType(7, "deployMidwareAndApplication");

    public static CostType getFeedback = new CostType(8, "getFeedback");

    public static CostType analyzeFeedbackAndLog = new CostType(9, "analyzeFeedbackAndLog");
}
