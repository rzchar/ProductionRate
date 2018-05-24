package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.costAndEffortType.CostTypeSet;
import edu.tongji.sse.qyd.costAndEffortType.EffortTypeSet;
import edu.tongji.sse.qyd.gitIssue.IssueInfo;
import edu.tongji.sse.qyd.spider.IssuesSpider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by qyd on 2018/5/21.
 */
public class IssueClosedSinglePeriodAnalyzer extends BasicSinglePeriodAnalyzer{
    public IssueClosedSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        super(datePeriod, urls);
    }

    @Override
    public AnalyzeResult statistic() {
        this.costTypeSet.clear();
        this.effortTypeSet.clear();
        IssuesSpider issuesSpider = new IssuesSpider();
        //List<IssueInfo> issueInfoList = new ArrayList<>();
        for(String url :urls){
            this.effortTypeSet.repairDefectEffort.addAddLine(1);
            IssueInfo issueInfo = issuesSpider.getEntityInfo(url);
            Set<String> labels = issueInfo.getLabels();
        }
        return new AnalyzeResult(this.costTypeSet, this.effortTypeSet);
    }
}
