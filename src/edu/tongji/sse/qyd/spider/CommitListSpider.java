package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;

import java.io.*;
import java.util.Date;

public class CommitListSpider extends ListSpider {

    protected int pagesCount = 0;

    protected CommitListSpider() {
        this.startURL = URLOfBasicAPI.commits;
        listFileName = Path.middleDataPath + File.separator + "commitGroups"
                + File.separator + "#commitList" + (new Date()).getTime() + ".txt";
        makeNewEmptyFile();
    }

    public CommitListSpider(String startURL, String fileName) {
        super(startURL, fileName);
    }

    private static String testString =
            "<https://api.github.com/repositories/32935745/commits?page=6>; rel=\"next\","
                    + " <https://api.github.com/repositories/32935745/commits?page=204>; rel=\"last\","
                    + " <https://api.github.com/repositories/32935745/commits?page=1>; rel=\"first\","
                    + " <https://api.github.com/repositories/32935745/commits?page=4>; rel=\"prev\"";

    //Set<String> urls = new HashSet<>();



    public static void main(String[] args) {
        CommitListSpider commitListSpider = new CommitListSpider();
        commitListSpider.getListToFile();
    }
}
