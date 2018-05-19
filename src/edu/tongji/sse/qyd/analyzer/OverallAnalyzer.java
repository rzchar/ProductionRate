package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Path;

import java.io.File;
import java.util.List;

public class OverallAnalyzer {

    public static void main(String[] args) {
        OverallAnalyzer an = new OverallAnalyzer();
        an.calculateEffortAndCost();
    }



    public List<CommitSinglePeriodAnalyzer> calculateEffortAndCost() {

        String commitGroupFolderPath = Path.middleDataPath + File.separator + "commitGroups" + File.separator + "byWeekExample1";

        CommitTotalPeriodAnalyzer commitTotalPeriodAnalyzer = new CommitTotalPeriodAnalyzer();

        String path = Path.middleDataPath + File.separator + "commitGroups" + File.separator + "byWeekWhole2";
        List<CommitSinglePeriodAnalyzer> commitSinglePeriodAnalyzers =commitTotalPeriodAnalyzer.getAnalyzerByPeroid(path, DatePeriod.getEclipseCheLifeTime());
        for (int iweeks = 0; iweeks < commitSinglePeriodAnalyzers.size(); iweeks++) {
            commitSinglePeriodAnalyzers.get(iweeks).statistic();
        }
        return commitSinglePeriodAnalyzers;
    }
}
