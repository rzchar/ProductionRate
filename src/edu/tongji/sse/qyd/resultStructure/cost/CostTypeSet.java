package edu.tongji.sse.qyd.resultStructure.cost;

/**
 * Created by qyd on 2018/5/13.
 */
public class CostTypeSet {
    public CostType errorTypeCost = new CostType(-1, "errorTypeCost");

    public CostType traditionalCodeCost = new CostType(0, "traditionalCodeCost");

    public CostType developingEnvironmentEstablish = new CostType(1, "developingEnvironmentEstablish");

    public CostType fetchReusableResourceWithCrawler = new CostType(2, "fetchReusableResourceWithCrawler");

    public CostType fetchReusableResourceWithVCS = new CostType(3, "fetchReusableResourceWithVCS");

    public CostType autoCompile = new CostType(4, "autoCompile");

    public CostType autoTest = new CostType(5, "autoTest");

    public CostType runtimeEnvironmentEstablish = new CostType(6, "runtimeEnvironmentEstablish");

    public CostType deployMidwareAndApplication = new CostType(7, "deployMidwareAndApplication");

    public CostType getFeedback = new CostType(8, "getFeedback");

    public CostType analyzeFeedbackAndLog = new CostType(9, "analyzeFeedbackAndLog");

    public CostType[] costTypeList = new CostType[]{
            this.errorTypeCost,
            this.traditionalCodeCost,
            this.developingEnvironmentEstablish,
            this.fetchReusableResourceWithCrawler,
            this.fetchReusableResourceWithVCS,
            this.autoCompile,
            this.autoTest,
            this.runtimeEnvironmentEstablish,
            this.deployMidwareAndApplication,
            this.getFeedback,
            this.analyzeFeedbackAndLog
    };

    public void clear() {
        for (CostType c : costTypeList) {
            c.resetAll();
        }
    }

    public void summary() {
        double sum = 0d;
        for (CostType c : costTypeList) {
            sum += c.getAddLineSum();
            System.out.print(c.getTypeString() + ":" + c.summary() + ";   ");
        }
        System.out.println();
        System.out.println("total added:" + sum);
    }
}
