package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.gitIssue.IssueInfo;
import edu.tongji.sse.qyd.resultStructure.AnalyzeResult;
import edu.tongji.sse.qyd.spider.IssuesSpider;

import java.util.List;
import java.util.Set;

/**
 * Created by qyd on 2018/5/22.
 */
public class IssueCreatedSinglePeriodAnalyzer extends BasicSinglePeriodAnalyzer {
    public IssueCreatedSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        super(datePeriod, urls);
    }

    @Override
    public AnalyzeResult statistic() {
        this.costTypeSet.clear();
        this.effortTypeSet.clear();
        IssuesSpider issuesSpider = new IssuesSpider();

        for(String url :urls){
            this.effortTypeSet.reportDefectEffort.addAddLine(1);
            this.costTypeSet.analyzeFeedbackAndLog.addAddLine(1);
            this.costTypeSet.getFeedback.addAddLine(1);
            IssueInfo issueInfo = issuesSpider.getEntityInfo(url);
            Set<String> labels = issueInfo.getLabels();
        }
        return new AnalyzeResult(this.costTypeSet, this.effortTypeSet);
    }
}
