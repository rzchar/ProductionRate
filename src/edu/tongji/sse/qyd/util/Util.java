package edu.tongji.sse.qyd.util;

import edu.tongji.sse.qyd.resultStructure.FileNames;
import edu.tongji.sse.qyd.spider.CommitSpider;
import edu.tongji.sse.qyd.spider.TagListSpider;

import java.io.File;
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

    public static Util Atom() {
        String projectAPIURL = "https://api.github.com/repos/atom/atom/";
        String folderName = "atom";
        return new Util(folderName, projectAPIURL, new ArrayList<>());
    }

    public static Util IntellijCommunity() {
        String projectAPIURL = "https://api.github.com/repos/JetBrains/intellij-community/";
        String folderName = "ic";//introduction chapter
        return new Util(folderName, projectAPIURL, new ArrayList<>());
    }

    public static Util Brackets() {
        String projectAPIURL = "https://api.github.com/repos/adobe/brackets/";
        String folderName = "brkt";
        return new Util(folderName, projectAPIURL, new ArrayList<>());
    }

    public static Util Vscode() {
        String projectAPIURL = "https://api.github.com/repos/Microsoft/vscode/";
        String folderName = "msvs";
        return new Util(folderName, projectAPIURL, new ArrayList<>());
    }

    public static Util ThisProject() {
        String projectAPIURL = "https://api.github.com/repos/rzchar/ProductionRate/";
        String folderName = "this";
        return new Util(folderName, projectAPIURL, new ArrayList<>());
    }


    private static Util currentInstance;

    public static Util getInstance() {
        if (currentInstance == null) {
            currentInstance = CheInstance();
        }
        return currentInstance;
    }

    public static void setInstance(String instanceName) {
        if (instanceName == "che") {
            currentInstance = CheInstance();
        }
        if (instanceName == "atom") {
            currentInstance = Atom();
        }
        if (instanceName == "ic") {
            currentInstance = IntellijCommunity();
        }
        if (instanceName == "brkt") {
            currentInstance = Brackets();
        }
        if (instanceName == "msvs") {
            currentInstance = Vscode();
        }
        if (instanceName == "this") {
            currentInstance = ThisProject();
        }
    }

    public static void main(String[] args) {

        String commitListFileName = "#commitList.txt";
        String issueListFileName = "#issueListAll.txt";
        String tagListFileName = "#tagList.txt";
        //String[] projects = new String[]{"che", "atom", "ic", "brkt", "msvs"}; ic unavailable
        String[] projects = new String[]{"che", "brkt", "msvs", "atom"};
        for (String pro : projects) {
            setInstance(pro);
//            CommitListSpider commitListSpider = new CommitListSpider(getInstance().getProjectAPIURL() + "commits", commitListFileName);
//            commitListSpider.getListAndWriteToFile();
            Util.log(Util.class, pro);
            Util.log(CommitSpider.class, "start");
            CommitSpider commitSpider = new CommitSpider();
            commitSpider.getAllEntity(commitListFileName);
            Util.log(CommitSpider.class, "end");
//            Util.log(IssuesSpider.class, "start");
//            IssuesSpider issuesSpider = new IssuesSpider();
//            issuesSpider.getAllEntity(issueListFileName);
//            Util.log(IssuesSpider.class, "end");
//            TagListSpider tgs = new TagListSpider(getInstance().getProjectAPIURL() + "tags", commitListFileName);
//            tgs.getListAndWriteToFile();
        }
    }

    static public void makeParentDir(File f) {
        File parent = f.getParentFile();
        if (!(parent.isDirectory() && parent.exists())) {
            parent.mkdirs();
        }
    }


}