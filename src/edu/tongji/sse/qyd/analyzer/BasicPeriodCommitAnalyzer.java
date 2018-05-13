package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.Util;
import edu.tongji.sse.qyd.costAndEffortType.BasicFilePattern;
import edu.tongji.sse.qyd.costAndEffortType.CostTypeSet;
import edu.tongji.sse.qyd.costAndEffortType.EffortTypeSet;
import edu.tongji.sse.qyd.gitCommit.GitCommitFileInfo;
import edu.tongji.sse.qyd.gitCommit.GitCommitInfo;
import edu.tongji.sse.qyd.spider.CommitSpider;

import java.util.*;

/**
 * Created by qyd on 2018/5/8.
 */
public class BasicPeriodCommitAnalyzer {

    private Date since;

    private Date until;

    private List<String> urls = null;

    private List<String> issues = null;

    private List<BasicFilePattern> patternList;

    private CostTypeSet costTypeSet = new CostTypeSet();

    private EffortTypeSet effortTypeSet = new EffortTypeSet();

    public BasicPeriodCommitAnalyzer(String fileName, List<String> urls) {
        this.urls = urls;
        this.since = Util.getSince(fileName);
        this.until = Util.getUntil(fileName);
        //System.out.println(this.since.toString() + " "+ this.until.toString());
        this.setPatterns();
    }

    public void statistic() {
        Set<String> authors = new HashSet<>();
        for (int iurl = 0; iurl < urls.size(); iurl++) {
            String url = urls.get(iurl);
            GitCommitInfo gitCommitInfo = CommitSpider.getInstance().getEntityInfo(url);
            List<GitCommitFileInfo> gitCommitFileInfoList = gitCommitInfo.getFiles();
            for (GitCommitFileInfo gitCommitFileInfo : gitCommitFileInfoList) {
                boolean succeedMatch = false;
                String fileName = gitCommitFileInfo.getFileName();
                String patch = gitCommitFileInfo.getPatch();
                for (BasicFilePattern basicFilePattern : this.patternList) {
                    if (basicFilePattern.isThisType(fileName, patch, "")) {
                        succeedMatch = true;
                    }
                }
                if (!succeedMatch) {
                    System.out.println("File Not Marked: " + fileName + " in " + gitCommitFileInfo.getGitHash());
                }
            }

            authors.add(gitCommitInfo.getAuthorId());

        }
        //System.out.println( "authors number:" + authors.size() + ";");
        System.out.println("=======");
    }

    public void setPatterns() {
        BasicFilePattern[] basicFilePatterns = new BasicFilePattern[]{
                new BasicFilePattern(
                        costTypeSet.traditionalCodeCost,
                        effortTypeSet.traditionalCode,
                        "\\.(java|js|go|html|ts|sql|css|svg|jsp)$"),

                new BasicFilePattern(
                        costTypeSet.traditionalCodeCost,
                        effortTypeSet.traditionalCode,
                        "(pom\\.xml|\\.helmignore)$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.tpl$"),
                new BasicFilePattern(
                        costTypeSet.traditionalCodeCost,
                        effortTypeSet.traditionalCode,
                        "che\\.properties$"),
                new BasicFilePattern(
                        costTypeSet.traditionalCodeCost,
                        effortTypeSet.traditionalCode,
                        "(?<!che)\\.properties$"),


                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.styl$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "LICENSE$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.gitignore$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.TckModule$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.ver$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.sh$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.env(.erb)?$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.yml$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.yaml$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.md$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "\\.json$"),
                new BasicFilePattern(costTypeSet.traditionalCodeCost, effortTypeSet.traditionalCode, "(?<!pom)\\.xml$"),

                new BasicFilePattern(costTypeSet.runtimeEnvironmentEstablish, effortTypeSet.autoScript, "^docker(files)?")

        };

        this.patternList = new ArrayList<>();
        for (int i = 0; i < basicFilePatterns.length; i++) {
            this.patternList.add(basicFilePatterns[i]);
        }
    }
}
