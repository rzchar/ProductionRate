package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.util.Util;

import java.io.File;
import java.util.Date;

public class CommitListSpider extends ListSpider {

    private static String testString =
            "<https://api.github.com/repositories/32935745/commits?page=6>; rel=\"next\","
                    + " <https://api.github.com/repositories/32935745/commits?page=204>; rel=\"last\","
                    + " <https://api.github.com/repositories/32935745/commits?page=1>; rel=\"first\","
                    + " <https://api.github.com/repositories/32935745/commits?page=4>; rel=\"prev\"";
    protected int pagesCount = 0;


    protected CommitListSpider() {
        this.startURL = Util.getInstance().getProjectAPIURL() + "commits";
        listFileName = Path.getMiddleDataPath() + File.separator + "commitGroups"
                + File.separator + "#commitList" + (new Date()).getTime() + ".txt";
        makeNewEmptyFile();
    }

    public CommitListSpider(String startURL, String fileName) {
        super(startURL, fileName);
        if (!fileName.contains(File.separator)) {
            listFileName = Path.getMiddleDataPath() + File.separator + "commitGroups"
                    + File.separator + fileName;
        }
        makeNewEmptyFile();
    }

    //Set<String> urls = new HashSet<>();

    public static void main(String[] args) {
        CommitListSpider commitListSpider = new CommitListSpider();
        commitListSpider.getListToFile();
    }
}
