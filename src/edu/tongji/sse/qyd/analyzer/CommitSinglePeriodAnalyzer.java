package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.costAndEffortType.BasicFilePattern;
import edu.tongji.sse.qyd.costAndEffortType.CostTypeSet;
import edu.tongji.sse.qyd.costAndEffortType.EffortTypeSet;
import edu.tongji.sse.qyd.gitCommit.GitCommitFileInfo;
import edu.tongji.sse.qyd.gitCommit.GitCommitInfo;
import edu.tongji.sse.qyd.spider.CommitSpider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by qyd on 2018/5/8.
 */
public class CommitSinglePeriodAnalyzer extends BasicSinglePeriodAnalyzer {

    private List<BasicFilePattern> patternList;

    public CommitSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        super(datePeriod, urls);
        //System.out.println(this.since.toString() + " "+ this.until.toString());
        this.setPatterns();
    }

    @Override
    public AnalyzeResult statistic() {
        this.costTypeSet.clear();
        this.effortTypeSet.clear();
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
        return new AnalyzeResult(this.costTypeSet, this.effortTypeSet);
    }

    public void setPatterns() {
        BasicFilePattern[] basicFilePatterns = new BasicFilePattern[]{
                new BasicFilePattern(
                        this.costTypeSet.traditionalCodeCost,
                        this.effortTypeSet.traditionalCodeEffort,
                        "\\.(java|js|go|html|ts|sql|css|svg|jsp)$"),

                new BasicFilePattern(
                        this.costTypeSet.runtimeEnvironmentEstablish,
                        this.effortTypeSet.autoScriptEffort,
                        "^docker(files)?"),

                new BasicFilePattern(
                        this.costTypeSet.developingEnvironmentEstablish,
                        this.effortTypeSet.autoScriptEffort,
                        "(pom\\.xml|\\.helmignore)$"),

                new BasicFilePattern(
                        this.costTypeSet.errorTypeCost,
                        this.effortTypeSet.errorTypeEffort, "\\.gitignore$"),


                new BasicFilePattern(
                        this.costTypeSet.errorTypeCost,
                        this.effortTypeSet.errorTypeEffort,
                        "\\.tpl$"),


                new BasicFilePattern(
                        this.costTypeSet.errorTypeCost,
                        this.effortTypeSet.errorTypeEffort,
                        "che\\.properties$"),
                new BasicFilePattern(
                        this.costTypeSet.errorTypeCost,
                        this.effortTypeSet.errorTypeEffort,
                        "(?<!che)\\.properties$"),


                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.styl$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "LICENSE$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.TckModule$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.ver$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.sh$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.env(.erb)?$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.yml$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.yaml$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.md$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.json$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "(?<!pom)\\.xml$")


        };

        this.patternList = new ArrayList<>();
        for (int i = 0; i < basicFilePatterns.length; i++) {
            this.patternList.add(basicFilePatterns[i]);
        }
    }
}
