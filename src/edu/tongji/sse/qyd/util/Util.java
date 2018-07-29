package edu.tongji.sse.qyd.util;

import edu.tongji.sse.qyd.model.Project;
import edu.tongji.sse.qyd.spider.CommitGrouper;

import java.io.File;

/**
 * Created by qyd on 2018/5/7.
 */
public class Util {


    public static void log(Class c, String message) {
        System.out.println("[" + c.getName() + "]" + message);
    }

    private static String commitListFileName = "#commitList.txt";


    public static void main(String[] args) {

        //String[] projects = new String[]{"che", "atom", "ic", "brkt", "msvs"}; ic unavailable
        String[] projects = new String[]{"atom"};
        for (String pro : projects) {
            Project.setInstance(pro);
            Project currentProject = Project.getInstance();
//            CommitListSpider commitListSpider = new CommitListSpider(getInstance().getProjectAPIURL() + "commits", commitListFileName);
//            commitListSpider.getListAndWriteToFile();
//            Util.log(Util.class, pro);
//            Util.log(CommitSpider.class, "start");
//            CommitSpider commitSpider = new CommitSpider();
//            commitSpider.getAllEntity(commitListFileName);
//            Util.log(CommitSpider.class, "end");
//            Util.log(IssueSpider.class, "start");
//            IssueSpider issueSpider = new IssueSpider();
//            issueSpider.getAllEntity(issueListFileName);
//            Util.log(IssueSpider.class, "end");
//            TagListSpider tgs = new TagListSpider(Project.getInstance().getProjectAPIURL() + "tags", tagListFileName);
//            tgs.getListAndWriteToFile();
            CommitGrouper commitGrouper = new CommitGrouper(commitListFileName);
            for (String versionName : currentProject.getDevCycle().keySet()) {
                commitGrouper.makeListAndWriteToFile(Path.getMiddleDataPath() + File.separator +
                        "commitGroups" + File.separator + versionName, currentProject.getDevCycle(versionName));
            }
        }
    }

    static public void makeParentDir(File f) {
        File parent = f.getParentFile();
        if (!(parent.isDirectory() && parent.exists())) {
            parent.mkdirs();
        }
    }


}
