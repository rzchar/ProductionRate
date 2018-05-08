package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;

import java.io.File;
import java.util.Date;

/**
 * Created by qyd on 2018/5/9.
 */
public class IssueListSpider extends ListSpider {
    protected int pagesCount = 0;

    protected IssueListSpider() {
        //this.startURL = URLOfBasicAPI.issuesAll;
        this.startURL = "https://api.github.com/repositories/32935745/issues?state=all&page=130";
        listFileName = Path.middleDataPath + File.separator + "issueGroups"
                + File.separator + "#issueListAll" + (new Date()).getTime() + ".txt";
        makeNewEmptyFile();
    }

    public IssueListSpider(String startURL, String fileName) {
        super(startURL, fileName);
    }

    public static void main(String[] args) {
        //IssueListSpider issueListSpider = new IssueListSpider();
        //issueListSpider.getListToFile();
        for(int i =4734;i>0;i--){
            System.out.println("https://api.github.com/repos/eclipse/che/issues/"+ i);
        }
    }
}
