package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.resultStructure.AnalyzeResult;
import edu.tongji.sse.qyd.resultStructure.info.InfoSet;

import java.util.List;

/**
 * Created by qyd on 2018/6/18.
 */
public class OtherInfoSinglePeriodAnalyzer extends  BasicSinglePeriodAnalyzer {
    public OtherInfoSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        super(datePeriod, urls);
    }

    @Override
    public AnalyzeResult statistic() {
        this.costTypeSet.clear();
        this.effortTypeSet.clear();
        InfoSet infoSet = new InfoSet();

        return new AnalyzeResult(this.costTypeSet, this.effortTypeSet, infoSet);
    }
}
