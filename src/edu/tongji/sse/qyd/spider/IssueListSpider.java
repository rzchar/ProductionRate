package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.util.Util;

import java.io.File;
import java.util.Date;

/**
 * Created by qyd on 2018/5/9.
 */
public class IssueListSpider extends ListSpider {
    protected int pagesCount = 0;

    public IssueListSpider(String startURL, String fileName) {
        super(startURL, fileName);
        if (!fileName.contains(File.separator)) {
            listFileName = Path.getMiddleDataPath() + File.separator + "issueGroupsAll"
                    + File.separator + fileName;
        }
        makeNewEmptyFile();
    }

    public IssueListSpider() {
        this.startURL = Util.getInstance().getProjectAPIURL() + "issues";
        listFileName = Path.getMiddleDataPath() + File.separator + "issueGroupsAll"
                + File.separator + "#issueList" + (new Date()).getTime() + ".txt";
        makeNewEmptyFile();
    }

    public static void main(String[] args) {
        //IssueListSpider issueListSpider = new IssueListSpider();
        //issueListSpider.getListAndWriteToFile();
        for (int i = 9984; i > 9645; i--) {
            System.out.println("https://api.github.com/repos/eclipse/che/issues/" + i);
        }
    }
}
