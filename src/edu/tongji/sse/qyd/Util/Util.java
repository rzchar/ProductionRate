package edu.tongji.sse.qyd.Util;

import edu.tongji.sse.qyd.analyzer.OverallAnalyzer;
import edu.tongji.sse.qyd.resultStructure.FileNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qyd on 2018/5/7.
 */
public class Util {

    private String projectFolderName;

    private String projectAPIURL;

    private List<FileNames> fileNamesList;

    public String getProjectFolderName() {
        return projectFolderName;
    }

    public String getProjectAPIURL() {
        return projectAPIURL;
    }

    public List<FileNames> getFileNamesList() {
        return fileNamesList;
    }

    public Util(String projectFolderName, String projectAPIURL, List<FileNames> fileNamesList) {
        this.projectFolderName = projectFolderName;
        this.projectAPIURL = projectAPIURL;
        this.fileNamesList = fileNamesList;
    }

    public static void log(Class c, String message) {
        System.out.println("[" + c.getName() + "]" + message);
    }

    public static Util CheInstance() {
        List<FileNames> fileNamesPrepare = new ArrayList<>();

        FileNames fileNamesWhole = new FileNames();
        fileNamesWhole.setOutputExcelName("analyze.xls");
        fileNamesWhole.setCommitGroupsFolderName("whole");
        fileNamesWhole.setIssueGroupsByClosedAtFolderName("whole");
        fileNamesWhole.setIssueGroupsByCreatedAtFolderName("whole");
        fileNamesWhole.setDatePeriodList(DatePeriod.getEclipseCheWholeLifeTime());
        fileNamesPrepare.add(fileNamesWhole);

        FileNames fileNamesV4 = new FileNames();
        fileNamesV4.setOutputExcelName("analyzeV4.xls");
        fileNamesV4.setCommitGroupsFolderName("V4");
        fileNamesV4.setIssueGroupsByClosedAtFolderName("V4");
        fileNamesV4.setIssueGroupsByCreatedAtFolderName("V4");
        fileNamesV4.setDatePeriodList(DatePeriod.getEclipseCheV4LifeTime());
        fileNamesPrepare.add(fileNamesV4);

        FileNames fileNamesV5 = new FileNames();
        fileNamesV5.setOutputExcelName("analyzeV5.xls");
        fileNamesV5.setCommitGroupsFolderName("V5");
        fileNamesV5.setIssueGroupsByClosedAtFolderName("V5");
        fileNamesV5.setIssueGroupsByCreatedAtFolderName("V5");
        fileNamesV5.setDatePeriodList(DatePeriod.getEclipseCheV5LifeTime());
        fileNamesPrepare.add(fileNamesV5);

        FileNames fileNamesV6 = new FileNames();
        fileNamesV6.setOutputExcelName("analyzeV6.xls");
        fileNamesV6.setCommitGroupsFolderName("V6");
        fileNamesV6.setIssueGroupsByClosedAtFolderName("V6");
        fileNamesV6.setIssueGroupsByCreatedAtFolderName("V6");
        fileNamesV6.setDatePeriodList(DatePeriod.getEclipseCheV6LifeTime());
        fileNamesPrepare.add(fileNamesV6);

        FileNames fileNamesV5E1 = new FileNames();
        fileNamesV5E1.setOutputExcelName("analyzeV5.1.xls");
        fileNamesV5E1.setCommitGroupsFolderName("V5.1");
        fileNamesV5E1.setIssueGroupsByClosedAtFolderName("V5.1");
        fileNamesV5E1.setIssueGroupsByCreatedAtFolderName("V5.1");
        fileNamesV5E1.setDatePeriodList(DatePeriod.getEclipseCheV5E1LifeTime());
        fileNamesPrepare.add(fileNamesV5E1);

        String projectAPIURL = "https://api.github.com/repos/eclipse/che/";

        String folderName = "che";

        return new Util(folderName, projectAPIURL, fileNamesPrepare);
    }

    private static Util currentInstance;

    public static Util getInstance() {
        if (currentInstance == null) {
            currentInstance = CheInstance();
        }
        return currentInstance;
    }

    public static void setInstance(String instanceName) {
    }
}
