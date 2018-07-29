package edu.tongji.sse.qyd.model;

import edu.tongji.sse.qyd.resultStructure.FileNames;
import edu.tongji.sse.qyd.util.DatePeriod;

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

    public Map<String, List<DatePeriod>> getDevCycle(){
        return  devCycle;
    }

    public List<DatePeriod> getDevCycle(String devCycleName){
        return  devCycle.get(devCycleName);
    }

    public void addDevCycle(String tag, List<DatePeriod> datePeriodList) {
        this.devCycle.put(tag, datePeriodList);
    }

    public Project(String projectFolderName, String projectAPIURL) {
        this.projectFolderName = projectFolderName;
        this.projectAPIURL = projectAPIURL;
        this.devCycle = new HashMap<>();
    }

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
        p.addDevCycle("V1", DatePeriod.getTimePeriodListByString("2015-06-25 00:00:00", "2018-05-30 00:00:00", 7));
        p.addDevCycle("V0", DatePeriod.getTimePeriodListByString("2012-01-20 00:00:00", "2015-06-25 00:00:00", 7));
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
        return p;
    }

    public static Project Vscode() {
        String projectAPIURL = "https://api.github.com/repos/Microsoft/vscode/";
        String folderName = "msvs";
        Project p = new Project(folderName, projectAPIURL);
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
