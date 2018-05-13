package edu.tongji.sse.qyd.costAndEffortType;

import edu.tongji.sse.qyd.costAndEffortType.CostType;

/**
 * Created by qyd on 2018/5/13.
 */
public class CostTypeSet {
    public CostType errorType = new CostType(-1, "errorType");

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
}
