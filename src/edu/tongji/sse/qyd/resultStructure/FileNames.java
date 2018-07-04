package edu.tongji.sse.qyd.resultStructure;

import edu.tongji.sse.qyd.Util.DatePeriod;

import java.util.List;

/**
 * Created by qyd on 2018/7/4.
 */
public class FileNames {
    String outputExcelName;
    String commitGroupsFolderName;
    String issueGroupsByCreatedAtFolderName;
    String issueGroupsByClosedAtFolderName;
    List<DatePeriod> datePeriodList;

    public FileNames(){}

    public FileNames(String outputExcelName,
                     String commitGroupsFolderName,
                     String issueGroupsByCreatedAtFolderName,
                     String issueGroupsByClosedAtFolderName,
                     List<DatePeriod> datePeriodList) {
        this.outputExcelName = outputExcelName;
        this.commitGroupsFolderName = commitGroupsFolderName;
        this.issueGroupsByCreatedAtFolderName = issueGroupsByCreatedAtFolderName;
        this.issueGroupsByClosedAtFolderName = issueGroupsByClosedAtFolderName;
        this.datePeriodList = datePeriodList;
    }

    public String getOutputExcelName() {
        return outputExcelName;
    }

    public void setOutputExcelName(String outputExcelName) {
        this.outputExcelName = outputExcelName;
    }

    public String getCommitGroupsFolderName() {
        return commitGroupsFolderName;
    }

    public void setCommitGroupsFolderName(String commitGroupsFolderName) {
        this.commitGroupsFolderName = commitGroupsFolderName;
    }

    public String getIssueGroupsByCreatedAtFolderName() {
        return issueGroupsByCreatedAtFolderName;
    }

    public void setIssueGroupsByCreatedAtFolderName(String issueGroupsByCreatedAtFolderName) {
        this.issueGroupsByCreatedAtFolderName = issueGroupsByCreatedAtFolderName;
    }

    public String getIssueGroupsByClosedAtFolderName() {
        return issueGroupsByClosedAtFolderName;
    }

    public void setIssueGroupsByClosedAtFolderName(String issueGroupsByClosedAtFolderName) {
        this.issueGroupsByClosedAtFolderName = issueGroupsByClosedAtFolderName;
    }

    public List<DatePeriod> getDatePeriodList() {
        return datePeriodList;
    }

    public void setDatePeriodList(List<DatePeriod> datePeriodList) {
        this.datePeriodList = datePeriodList;
    }
}
