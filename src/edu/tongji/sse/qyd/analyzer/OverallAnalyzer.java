package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.util.Util;
import edu.tongji.sse.qyd.resultStructure.AnalyzeResult;
import edu.tongji.sse.qyd.resultStructure.FileNames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OverallAnalyzer {

    public static void main(String[] args) {


        FileNames fileNamesWhole = new FileNames();
        fileNamesWhole.setOutputExcelName("analyze.xls");
        fileNamesWhole.setCommitGroupsFolderName("whole");
        fileNamesWhole.setIssueGroupsByClosedAtFolderName("whole");
        fileNamesWhole.setIssueGroupsByCreatedAtFolderName("whole");
        fileNamesWhole.setDatePeriodList(DatePeriod.getEclipseCheWholeLifeTime());
        OverallAnalyzer wholeAnalyzer = new OverallAnalyzer();
        wholeAnalyzer.calculateEffortAndCost(fileNamesWhole);

        FileNames fileNamesV4 = new FileNames();
        fileNamesV4.setOutputExcelName("analyzeV4.xls");
        fileNamesV4.setCommitGroupsFolderName("V4");
        fileNamesV4.setIssueGroupsByClosedAtFolderName("V4");
        fileNamesV4.setIssueGroupsByCreatedAtFolderName("V4");
        fileNamesV4.setDatePeriodList(DatePeriod.getEclipseCheV4LifeTime());
        OverallAnalyzer v4Analyzer = new OverallAnalyzer();
        v4Analyzer.calculateEffortAndCost(fileNamesV4);

        FileNames fileNamesV5 = new FileNames();
        fileNamesV5.setOutputExcelName("analyzeV5.xls");
        fileNamesV5.setCommitGroupsFolderName("V5");
        fileNamesV5.setIssueGroupsByClosedAtFolderName("V5");
        fileNamesV5.setIssueGroupsByCreatedAtFolderName("V5");
        fileNamesV5.setDatePeriodList(DatePeriod.getEclipseCheV5LifeTime());
        OverallAnalyzer v5Analyzer = new OverallAnalyzer();
        v5Analyzer.calculateEffortAndCost(fileNamesV5);

        FileNames fileNamesV6 = new FileNames();
        fileNamesV6.setOutputExcelName("analyzeV6.xls");
        fileNamesV6.setCommitGroupsFolderName("V6");
        fileNamesV6.setIssueGroupsByClosedAtFolderName("V6");
        fileNamesV6.setIssueGroupsByCreatedAtFolderName("V6");
        fileNamesV6.setDatePeriodList(DatePeriod.getEclipseCheV6LifeTime());
        OverallAnalyzer v6Analyzer = new OverallAnalyzer();
        v6Analyzer.calculateEffortAndCost(fileNamesV6);

        FileNames fileNamesV5E1 = new FileNames();
        fileNamesV5E1.setOutputExcelName("analyzeV5.1.xls");
        fileNamesV5E1.setCommitGroupsFolderName("V5.1");
        fileNamesV5E1.setIssueGroupsByClosedAtFolderName("V5.1");
        fileNamesV5E1.setIssueGroupsByCreatedAtFolderName("V5.1");
        fileNamesV5E1.setDatePeriodList(DatePeriod.getEclipseCheV5E1LifeTime());
        OverallAnalyzer V5E1Analyzer = new OverallAnalyzer();
        V5E1Analyzer.calculateEffortAndCost(fileNamesV5E1);
    }
    /* Time Point
     *  4.0.0-RC4  2016-02-15T21:12:15Z
     *  5.0.0-M1  2016-09-14T15:50:16Z
     *  6.0.0-M1  2017-11-02T12:39:49Z
     */


    public List<CommitSinglePeriodAnalyzer> calculateEffortAndCost(FileNames fileNames) {

        ExcelWriter excelWriter = new ExcelWriter(fileNames.getOutputExcelName());

        CommitTotalPeriodAnalyzer commitTotalPeriodAnalyzer = new CommitTotalPeriodAnalyzer();
        IssuesCreatedTotalPeriodAnalyzer issuesCreatedTotalPeriodAnalyzer = new IssuesCreatedTotalPeriodAnalyzer();
        IssuesClosedTotalPeriodAnalyzer issueClosedTotalPeriodAnalyzer = new IssuesClosedTotalPeriodAnalyzer();


        String commitGroupFolderPath = Path.getMiddleDataPath() + File.separator + "commitGroups" + File.separator + fileNames.getCommitGroupsFolderName();
        String issuesClosedFolderPath = Path.getMiddleDataPath() + File.separator + "issueGroupsByClosedAt" + File.separator + fileNames.getIssueGroupsByClosedAtFolderName();
        String issuesCreatedFolderPath = Path.getMiddleDataPath() + File.separator + "issueGroupsByCreatedAt" + File.separator + fileNames.getIssueGroupsByCreatedAtFolderName();
        //String commitGroupFolderPath = Path.getMiddleDataPath() + File.separator + "commitGroups" + File.separator + "byWeekExample1";

        List<DatePeriod> eclipseCheLifeTime = fileNames.getDatePeriodList();
        List<CommitSinglePeriodAnalyzer> commitSinglePeriodAnalyzers = commitTotalPeriodAnalyzer.getAnalyzerByPeroid(commitGroupFolderPath, eclipseCheLifeTime);
        List<IssueCreatedSinglePeriodAnalyzer> issueCreatedSinglePeriodAnalyzers = issuesCreatedTotalPeriodAnalyzer.getAnalyzerByPeroid(issuesCreatedFolderPath, eclipseCheLifeTime);
        List<IssueClosedSinglePeriodAnalyzer> issueClosedSinglePeriodAnalyzers = issueClosedTotalPeriodAnalyzer.getAnalyzerByPeroid(issuesClosedFolderPath,eclipseCheLifeTime);

        Util.log(this.getClass(), "eclipseCheLifeTime" + " size " + eclipseCheLifeTime.size());
        Util.log(this.getClass(), "commitGroupFolderPath" + " size " + commitSinglePeriodAnalyzers.size());
        Util.log(this.getClass(), "issueCreatedSinglePeriodAnalyzers"+ " size " + issueCreatedSinglePeriodAnalyzers.size());
        Util.log(this.getClass(), "issueClosedSinglePeriodAnalyzers" + " size " + issueClosedSinglePeriodAnalyzers.size());

        for (int iWeeks = 0; iWeeks < eclipseCheLifeTime.size(); iWeeks++) {
            Util.log(this.getClass(), eclipseCheLifeTime.get(iWeeks).getSinceUntilFileName("",""));
            List<AnalyzeResult> analyzeResultInThisWeek = new ArrayList<>();
            analyzeResultInThisWeek.add(commitSinglePeriodAnalyzers.get(iWeeks).statistic());
            analyzeResultInThisWeek.add(issueCreatedSinglePeriodAnalyzers.get(iWeeks).statistic());
            analyzeResultInThisWeek.add(issueClosedSinglePeriodAnalyzers.get(iWeeks).statistic());
            excelWriter.addRow(analyzeResultInThisWeek, eclipseCheLifeTime.get(iWeeks));
        }
        excelWriter.close();
        return null;
    }
}
