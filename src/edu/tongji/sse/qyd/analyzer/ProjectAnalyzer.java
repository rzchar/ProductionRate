package edu.tongji.sse.qyd.analyzer;

import com.sun.istack.NotNull;
import edu.tongji.sse.qyd.model.Project;
import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.util.Util;
import edu.tongji.sse.qyd.resultStructure.AnalyzeResult;
import edu.tongji.sse.qyd.resultStructure.FileNames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectAnalyzer {

    public static void main(String[] args) {

    }

    private Project project;

    public ProjectAnalyzer(@NotNull Project project){
        this.project = project;
    }


    public List<CommitSinglePeriodAnalyzer> calculateEffortAndCost(FileNames fileNames,String version) {


        ExcelWriter excelWriter = new ExcelWriter(fileNames.getOutputExcelName());
        List<DatePeriod> lifeTime = project.getDevCycle(version);

        CommitTotalPeriodAnalyzer commitTotalPeriodAnalyzer = new CommitTotalPeriodAnalyzer();
        IssuesCreatedTotalPeriodAnalyzer issuesCreatedTotalPeriodAnalyzer = new IssuesCreatedTotalPeriodAnalyzer();
        IssuesClosedTotalPeriodAnalyzer issueClosedTotalPeriodAnalyzer = new IssuesClosedTotalPeriodAnalyzer();


        String commitGroupFolderPath = Path.getCommitGroupsFolder() + File.separator + fileNames.getCommitGroupsFolderName();
        String issuesClosedFolderPath = Path.getIssuerGroupsByClosedAtFolder()+ File.separator + fileNames.getIssueGroupsByClosedAtFolderName();
        String issuesCreatedFolderPath = Path.getIssuerGroupsByCreatedAtFolder()+ File.separator + fileNames.getIssueGroupsByCreatedAtFolderName();
        //String commitGroupFolderPath = Path.getMiddleDataPath() + File.separator + "commitGroups" + File.separator + "byWeekExample1";



        List<CommitSinglePeriodAnalyzer> commitSinglePeriodAnalyzers = commitTotalPeriodAnalyzer.getAnalyzerByPeroid(commitGroupFolderPath, lifeTime);
        List<IssueCreatedSinglePeriodAnalyzer> issueCreatedSinglePeriodAnalyzers = issuesCreatedTotalPeriodAnalyzer.getAnalyzerByPeroid(issuesCreatedFolderPath, lifeTime);
        List<IssueClosedSinglePeriodAnalyzer> issueClosedSinglePeriodAnalyzers = issueClosedTotalPeriodAnalyzer.getAnalyzerByPeroid(issuesClosedFolderPath,lifeTime);

        Util.log(this.getClass(), "lifeTime" + " size " + lifeTime.size());
        Util.log(this.getClass(), "commitGroupFolderPath" + " size " + commitSinglePeriodAnalyzers.size());
        Util.log(this.getClass(), "issueCreatedSinglePeriodAnalyzers"+ " size " + issueCreatedSinglePeriodAnalyzers.size());
        Util.log(this.getClass(), "issueClosedSinglePeriodAnalyzers" + " size " + issueClosedSinglePeriodAnalyzers.size());

        for (int iWeeks = 0; iWeeks < lifeTime.size(); iWeeks++) {
            Util.log(this.getClass(), lifeTime.get(iWeeks).getSinceUntilFileName("",""));
            List<AnalyzeResult> analyzeResultInThisWeek = new ArrayList<>();
            analyzeResultInThisWeek.add(commitSinglePeriodAnalyzers.get(iWeeks).statistic());
            analyzeResultInThisWeek.add(issueCreatedSinglePeriodAnalyzers.get(iWeeks).statistic());
            analyzeResultInThisWeek.add(issueClosedSinglePeriodAnalyzers.get(iWeeks).statistic());
            excelWriter.addRow(analyzeResultInThisWeek, lifeTime.get(iWeeks));
        }
        excelWriter.close();
        return null;
    }
}
