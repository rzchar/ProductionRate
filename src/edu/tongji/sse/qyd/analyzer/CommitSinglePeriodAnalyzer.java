package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Util;
import edu.tongji.sse.qyd.resultStructure.AnalyzeResult;
import edu.tongji.sse.qyd.resultStructure.BasicFilePattern;
import edu.tongji.sse.qyd.model.GitCommitFileInfo;
import edu.tongji.sse.qyd.model.CommitInfo;
import edu.tongji.sse.qyd.resultStructure.info.Contributor;
import edu.tongji.sse.qyd.resultStructure.info.InfoSet;
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

    private static String resourceFileSurfix = "\\.(jpg|gif|png|svg)$";

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
            Contributor contributor = new Contributor();
            String url = urls.get(iurl);
            CommitInfo commitInfo = CommitSpider.getInstance().getEntityInfo(url);
            List<GitCommitFileInfo> gitCommitFileInfoList = commitInfo.getFiles();

            contributor.addAuthor(commitInfo.getAuthorId());
            for (GitCommitFileInfo gitCommitFileInfo : gitCommitFileInfoList) {
                boolean succeedMatch = false;
                String fileName = gitCommitFileInfo.getFileName();
                String patch = gitCommitFileInfo.getPatch();
                for (BasicFilePattern basicFilePattern : this.patternList) {
                    if (basicFilePattern.isThisType(fileName, patch, "")) {
                        basicFilePattern.getCostType().addCommitData(gitCommitFileInfo);
                        basicFilePattern.getEffortType().addCommitData(gitCommitFileInfo);
                        succeedMatch = true;
                        break;
                    }
                }
                if (!succeedMatch) {
                    Util.log(this.getClass(), "File Not Marked: " + fileName + " in " + gitCommitFileInfo.getGitHash());
                }
            }

            authors.add(commitInfo.getAuthorId());

        }
        //System.out.println( "authors number:" + authors.size() + ";");
        InfoSet infoSet = new InfoSet();
        infoSet.setCommitterAmount(authors.size());

        return new AnalyzeResult(this.costTypeSet, this.effortTypeSet, infoSet);
    }

    public void setPatterns() {
        BasicFilePattern[] basicFilePatterns = new BasicFilePattern[]{
                new BasicFilePattern(
                        this.costTypeSet.sourceCodeCost,
                        this.effortTypeSet.reusableCodeEffort,
                        "\\.(java|js|go|html|ts|sql|css|less|svg|jsp|styl|ts)$"
                ),

                new BasicFilePattern(
                        //for resource files
                        this.costTypeSet.errorTypeCost,
                        this.effortTypeSet.errorTypeEffort,
                        resourceFileSurfix + "|" + resourceFileSurfix.toUpperCase() + "|"
                        + "/src/(main|test)/resources/"
                ){
                    @Override
                    public GitCommitFileInfo analyzedFileInfo(GitCommitFileInfo gitCommitFileInfo) {
                        gitCommitFileInfo.setAdditionNum(0);
                        gitCommitFileInfo.setChangeNum(0);
                        gitCommitFileInfo.setDeletionNum(0);
                        return gitCommitFileInfo;
                    }
                },

                new BasicFilePattern(
                        this.costTypeSet.deployMidwareAndApplication,
                        this.effortTypeSet.autoScriptEffort,
                        "^[Dd]ocker(files)?"),

                new BasicFilePattern(
                        this.costTypeSet.deployMidwareAndApplication,
                        this.effortTypeSet.autoScriptEffort,
                        "^deploy"),

                new BasicFilePattern(
                        this.costTypeSet.developingEnvironmentEstablish,
                        this.effortTypeSet.autoScriptEffort,
                        "(pom\\.xml|\\.helmignore)$"),

                new BasicFilePattern(
                        this.costTypeSet.autoCompile,
                        this.effortTypeSet.autoScriptEffort,
                        "\\.sh$"),

                new BasicFilePattern(
                        this.costTypeSet.deployMidwareAndApplication,
                        this.effortTypeSet.autoScriptEffort,
                        "che\\.properties$"),

                new BasicFilePattern(
                        this.costTypeSet.runtimeEnvironmentEstablish,
                        this.effortTypeSet.autoScriptEffort,
                        "\\.md$"),


                new BasicFilePattern(
                        this.costTypeSet.deployMidwareAndApplication,
                        this.effortTypeSet.autoScriptEffort,
                        "\\.(yml|yaml)$"),

                new BasicFilePattern(
                        this.costTypeSet.deployMidwareAndApplication,
                        this.effortTypeSet.autoScriptEffort,
                        "(?<!pom)\\.xml$"),

                new BasicFilePattern(
                        this.costTypeSet.autoTest,
                        this.effortTypeSet.autoScriptEffort,
                        "org\\.testng\\.ITestNGListener$"
                ),

                new BasicFilePattern(
                        this.costTypeSet.autoTest,
                        this.effortTypeSet.autoScriptEffort,
                        "/src/test/(?!resources)"
                ),
   /*================================================================================================*/

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
                        "(?<!che)\\.properties$"),

                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "LICENSE$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.TckModule$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.ver$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.env(.erb)?$"),
                new BasicFilePattern(this.costTypeSet.errorTypeCost, this.effortTypeSet.errorTypeEffort, "\\.json$")


        };

        this.patternList = new ArrayList<>();
        for (int i = 0; i < basicFilePatterns.length; i++) {
            this.patternList.add(basicFilePatterns[i]);
        }
    }
}
