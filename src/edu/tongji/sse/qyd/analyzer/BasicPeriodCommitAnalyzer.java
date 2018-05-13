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
                        basicFilePattern.getCostType().addCommitData(gitCommitFileInfo);
                        basicFilePattern.getEffortType().addCommitData(gitCommitFileInfo);
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
        this.costTypeSet.summary();
        this.effortTypeSet.summary();
        System.out.println("=======");
    }

    public void setPatterns() {
        BasicFilePattern[] basicFilePatterns = new BasicFilePattern[]{
                new BasicFilePattern(
                        costTypeSet.traditionalCodeCost,
                        effortTypeSet.traditionalCodeEffort,
                        "\\.(java|js|go|html|ts|sql|css|svg|jsp)$"),

                new BasicFilePattern(
                        costTypeSet.runtimeEnvironmentEstablish,
                        effortTypeSet.autoScriptEffort,
                        "^docker(files)?"),

                new BasicFilePattern(
                        costTypeSet.developingEnvironmentEstablish,
                        effortTypeSet.autoScriptEffort,
                        "(pom\\.xml|\\.helmignore)$"),

                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.gitignore$"),




                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.tpl$"),
                new BasicFilePattern(
                        costTypeSet.errorTypeCost,
                        effortTypeSet.errorTypeEffort,
                        "che\\.properties$"),
                new BasicFilePattern(
                        costTypeSet.errorTypeCost,
                        effortTypeSet.errorTypeEffort,
                        "(?<!che)\\.properties$"),


                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.styl$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "LICENSE$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.TckModule$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.ver$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.sh$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.env(.erb)?$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.yml$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.yaml$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.md$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "\\.json$"),
                new BasicFilePattern(costTypeSet.errorTypeCost, effortTypeSet.errorTypeEffort, "(?<!pom)\\.xml$")


        };

        this.patternList = new ArrayList<>();
        for (int i = 0; i < basicFilePatterns.length; i++) {
            this.patternList.add(basicFilePatterns[i]);
        }
    }
}
