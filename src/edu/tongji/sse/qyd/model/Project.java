package edu.tongji.sse.qyd.model;

import edu.tongji.sse.qyd.analyzer.ProjectAnalyzer;
import edu.tongji.sse.qyd.resultStructure.FileNames;
import edu.tongji.sse.qyd.spider.*;
import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Path;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qyd on 2018/7/25.
 */
public class Project {

    private String projectFolderName;

    private String projectAPIURL;

    private Map<String, List<DatePeriod>> devCycle;

    public String getProjectFolderName() {
        return projectFolderName;
    }

    public String getProjectAPIURL() {
        return projectAPIURL;
    }

    public Map<String, List<DatePeriod>> getDevCycle() {
        return devCycle;
    }

    public List<DatePeriod> getDevCycle(String devCycleName) {
        return devCycle.get(devCycleName);
    }

    public void addDevCycle(String tag, List<DatePeriod> datePeriodList) {
        this.devCycle.put(tag, datePeriodList);
    }

    public Project(String projectFolderName, String projectAPIURL) {
        this.projectFolderName = projectFolderName;
        this.projectAPIURL = projectAPIURL;
        this.devCycle = new HashMap<>();
    }

    public void fetchListsFromGithub() {
        CommitListSpider commitListSpider = new CommitListSpider(getInstance().getProjectAPIURL() + "commits", Path.commitListFileName);
        commitListSpider.getListAndWriteToFile();
        IssueListSpider issueListSpider = new IssueListSpider(getInstance().getProjectAPIURL() + "issues", Path.issueListFileName);
        issueListSpider.getListAndWriteToFile();
        TagListSpider tgs = new TagListSpider(Project.getInstance().getProjectAPIURL() + "tags", Path.tagListFileName);
        tgs.getListAndWriteToFile();
    }

    public void fetchInfoFromGithub() {
        CommitSpider commitSpider = new CommitSpider();
        commitSpider.getAllEntity(Path.commitListFileName);
        IssueSpider issueSpider = new IssueSpider();
        issueSpider.getAllEntity(Path.issueListFileName);
    }

    public void groupTheInfo() {
        CommitGrouper commitGrouper = new CommitGrouper(Path.commitListFileName);
        IssueGrouper issueCreatedAtGrouper = new IssueGrouper(IssueGrouper.GroupBy.createdTime, Path.issueListFileName);
        IssueGrouper issueClosedAtGrouper = new IssueGrouper(IssueGrouper.GroupBy.closedTime, Path.issueListFileName);
        for (String versionName : this.devCycle.keySet()) {
            List<DatePeriod> dps = this.devCycle.get(versionName);
            commitGrouper.makeListAndWriteToFile(Path.getCommitGroupsFolder()
                    + File.separator + versionName, dps);
            issueCreatedAtGrouper.makeListAndWriteToFile(Path.getIssuerGroupsByCreatedAtFolder()
                    + File.separator + versionName, dps);
            issueClosedAtGrouper.makeListAndWriteToFile(Path.getIssuerGroupsByClosedAtFolder()
                    + File.separator + versionName, dps);
        }

    }

    public void analyze() {
        ProjectAnalyzer projectAnalyzer = new ProjectAnalyzer(currentInstance);
        for (String versionName : this.devCycle.keySet()) {
            projectAnalyzer.calculateEffortAndCost(versionName);
        }
    }

    /*============================================*/

    public static Project CheInstance() {
        List<FileNames> fileNamesPrepare = new ArrayList<>();

        FileNames fileNamesWhole = new FileNames();
        fileNamesWhole.setOutputExcelName("analyze.xls");
        fileNamesWhole.setCommitGroupsFolderName("whole");
        fileNamesWhole.setIssueGroupsByClosedAtFolderName("whole");
        fileNamesWhole.setIssueGroupsByCreatedAtFolderName("whole");
        fileNamesWhole.setDatePeriodList(DatePeriod.getEclipseCheWholeLifeTime());
        fileNamesPrepare.add(fileNamesWhole);

        String folderName = "che";
        String projectAPIURL = "https://api.github.com/repos/eclipse/che/";
        Project p = new Project(folderName, projectAPIURL);
        p.addDevCycle("V4", DatePeriod.getTimePeriodListByString("2016-06-15 00:00:00", "2016-09-14 00:00:00", 7));
        p.addDevCycle("V5", DatePeriod.getTimePeriodListByString("2016-09-14 00:00:00", "2017-11-02 00:00:00", 7));
        p.addDevCycle("V5.1", DatePeriod.getTimePeriodListByString("2016-09-14 00:00:00", "2017-11-02 00:00:00", 1));
        p.addDevCycle("V6", DatePeriod.getTimePeriodListByString("2017-11-02 00:00:00", "2018-05-30 00:00:00", 7));
        return p;
    }

    public static Project Atom() {
        String projectAPIURL = "https://api.github.com/repos/atom/atom/";
        String folderName = "atom";
        Project p = new Project(folderName, projectAPIURL);
        p.addDevCycle("V0", DatePeriod.getTimePeriodListByString("2012-01-20 00:00:00", "2015-06-25 00:00:00", 7));
        p.addDevCycle("V1", DatePeriod.getTimePeriodListByString("2015-06-25 00:00:00", "2018-05-30 00:00:00", 7));
        return p;
    }

    public static Project IntellijCommunity() {
        String projectAPIURL = "https://api.github.com/repos/JetBrains/intellij-community/";
        String folderName = "ic";//introduction chapter
        Project p = new Project(folderName, projectAPIURL);
        return p;
    }

    public static Project Brackets() {
        String projectAPIURL = "https://api.github.com/repos/adobe/brackets/";
        String folderName = "brkt";
        Project p = new Project(folderName, projectAPIURL);
        p.addDevCycle("V0", DatePeriod.getTimePeriodListByString("2011-12-07 00:00:00", "2014-10-25 00:00:00", 7));
        p.addDevCycle("V1", DatePeriod.getTimePeriodListByString("2014-10-25 00:00:00", "2018-05-30 00:00:00", 7));
        return p;
    }

    public static Project Vscode() {
        String projectAPIURL = "https://api.github.com/repos/Microsoft/vscode/";
        String folderName = "msvs";
        Project p = new Project(folderName, projectAPIURL);
        p.addDevCycle("V0", DatePeriod.getTimePeriodListByString("2015-09-03 00:00:00", "2016-04-14 00:00:00", 7));
        p.addDevCycle("V1", DatePeriod.getTimePeriodListByString("2016-04-14 00:00:00", "2018-05-30 00:00:00", 7));
        return p;
    }

    public static Project ThisProject() {
        String projectAPIURL = "https://api.github.com/repos/rzchar/ProductionRate/";
        String folderName = "this";
        Project p = new Project(folderName, projectAPIURL);
        return p;
    }


    private static Project currentInstance;

    public static Project getInstance() {
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
}
