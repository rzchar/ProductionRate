package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.spider.IssueSpider;
import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.model.IssueInfo;
import edu.tongji.sse.qyd.resultStructure.AnalyzeResult;

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
        IssueSpider issueSpider = new IssueSpider();
        //List<IssueInfo> issueInfoList = new ArrayList<>();
        for(String url :urls){
            this.effortTypeSet.repairDefectEffort.addAddLine(1);
            IssueInfo issueInfo = issueSpider.getEntityInfo(url);
            Set<String> labels = issueInfo.getLabels();
        }
        return new AnalyzeResult(this.costTypeSet, this.effortTypeSet);
    }
}
