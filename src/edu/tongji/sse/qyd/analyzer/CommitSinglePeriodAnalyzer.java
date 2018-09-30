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

    public CommitSinglePeriodAnalyzer(DatePeriod datePeriod, List<String> urls) {
        super(datePeriod, urls);
        this.patternList = new ArrayList<>();
        //System.out.println(this.since.toString() + " "+ this.until.toString());
        //this.setPatterns();
    }

    public void addFilePatterns(BasicFilePattern[] inputFilePatterns){
        for (int i = 0; i < inputFilePatterns.length; i++) {
            this.patternList.add(inputFilePatterns[i]);
        }
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

}
