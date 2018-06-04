package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Path;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OverallAnalyzer {

    public static void main(String[] args) {
        OverallAnalyzer an = new OverallAnalyzer();
        an.calculateEffortAndCost();
    }



    public List<CommitSinglePeriodAnalyzer> calculateEffortAndCost() {


        ExcelWriter excelWriter = new ExcelWriter("analyze.xls");

        CommitTotalPeriodAnalyzer commitTotalPeriodAnalyzer = new CommitTotalPeriodAnalyzer();
        IssuesCreatedTotalPeriodAnalyzer issuesCreatedTotalPeriodAnalyzer = new IssuesCreatedTotalPeriodAnalyzer();
        IssuesClosedTotalPeriodAnalyzer issueClosedTotalPeriodAnalyzer = new IssuesClosedTotalPeriodAnalyzer();


        String commitGroupFolderPath = Path.middleDataPath + File.separator + "commitGroups" + File.separator + "byWeekWhole2";
        String issuesClosedFolderPath = Path.middleDataPath + File.separator + "issueGroups" + File.separator + "GroupByClosedAt";
        String issuesCreatedFolderPath = Path.middleDataPath + File.separator + "issueGroups" + File.separator + "GroupByCreatedAt";
        //String commitGroupFolderPath = Path.middleDataPath + File.separator + "commitGroups" + File.separator + "byWeekExample1";

        List<DatePeriod> eclipseCheLifeTime = DatePeriod.getEclipseCheLifeTime();
        List<CommitSinglePeriodAnalyzer> commitSinglePeriodAnalyzers = commitTotalPeriodAnalyzer.getAnalyzerByPeroid(commitGroupFolderPath, eclipseCheLifeTime);
        List<IssueCreatedSinglePeriodAnalyzer> issueCreatedSinglePeriodAnalyzers = issuesCreatedTotalPeriodAnalyzer.getAnalyzerByPeroid(issuesCreatedFolderPath, eclipseCheLifeTime);
        List<IssueClosedSinglePeriodAnalyzer> issueClosedSinglePeriodAnalyzers = issueClosedTotalPeriodAnalyzer.getAnalyzerByPeroid(issuesClosedFolderPath,eclipseCheLifeTime);

        System.out.println(eclipseCheLifeTime.size());
        System.out.println(commitSinglePeriodAnalyzers.size());
        System.out.println(issueCreatedSinglePeriodAnalyzers.size());
        System.out.println(issueClosedSinglePeriodAnalyzers.size());
        for (int iweeks = 0; iweeks < eclipseCheLifeTime.size(); iweeks++) {
            System.out.println(eclipseCheLifeTime.get(iweeks).getSinceUntilFileName("",""));
            List<AnalyzeResult> analyzeResultInThisWeek = new ArrayList<>();
            analyzeResultInThisWeek.add(commitSinglePeriodAnalyzers.get(iweeks).statistic());
            analyzeResultInThisWeek.add(issueCreatedSinglePeriodAnalyzers.get(iweeks).statistic());
            analyzeResultInThisWeek.add(issueClosedSinglePeriodAnalyzers.get(iweeks).statistic());
            excelWriter.addRow(analyzeResultInThisWeek, eclipseCheLifeTime.get(iweeks));
        }

        excelWriter.close();
        return null;
    }
}
