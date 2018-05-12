package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.gitCommit.GitCommitFileInfo;
import edu.tongji.sse.qyd.gitCommit.GitCommitInfo;
import edu.tongji.sse.qyd.spider.CommitSpider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/8.
 */
public class WeekCommitAnalyzer {

    private String strTimeProid = "";

    private List<String> urls = null;

    public WeekCommitAnalyzer(String strTimeProid, List<String> urls) {
        this.urls = urls;
        this.strTimeProid = strTimeProid.replace("commitList","").replace(".txt","");
    }

    public void statistic(){
        int[] sum = new int[]{0, 0, 0, 0};
        Pattern[] patterns = new Pattern[]{Pattern.compile(""), Pattern.compile("\\.sh$"), Pattern.compile("\\.java$"),
                Pattern.compile("\\.mvn$")};
        Set<String> authors = new HashSet<>();
        //Set<String> types = new HashSet<>();
        List<GitCommitFileInfo> filesInWeek = new ArrayList<GitCommitFileInfo>();

        for (int iurl = 0; iurl < urls.size(); iurl++) {
            String url = urls.get(iurl);
            GitCommitInfo gitCommitInfo = CommitSpider.getInstance().getEntityInfo(url);
            //for (int ipatterns = 0; ipatterns < patterns.length; ipatterns++) {
            //sum[ipatterns] += gitCommitInfo.sumChanges(patterns[ipatterns]);
            //}
            filesInWeek.addAll(gitCommitInfo.getFiles());
            authors.add(gitCommitInfo.getAuthorId());
            gitCommitInfo.printFileTypes();

        }
        System.out.println("time:" + strTimeProid + ";" + "authors number:" + authors.size() + ";");

        System.out.println();
    }
}