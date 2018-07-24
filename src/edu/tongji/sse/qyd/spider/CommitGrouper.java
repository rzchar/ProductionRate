package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Util;
import edu.tongji.sse.qyd.gitCommit.CommitInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qyd on 2018/5/6.
 */
public class CommitGrouper implements   EntityGrouper<CommitInfo> {
    private String commitListFileName;

    public CommitGrouper(String commitListFileName) {
        this.commitListFileName = commitListFileName;
    }

    @Override
    public Map<DatePeriod, List<CommitInfo>> getIssueGroupByTime(List<DatePeriod> datePeriodList) {
        List<CommitInfo> commitInfoList = CommitSpider.getInstance().getAllEntity(commitListFileName);
        Map<DatePeriod, List<CommitInfo>> commitsByCreatedTime = new HashMap<>();
        for (CommitInfo commit : commitInfoList) {
            Util.log(IssueGrouper.class, commit.getTime());
            for (DatePeriod datePeriod : datePeriodList) {
                if (datePeriod.contain(DatePeriod.getDateFromISO8601(commit.getTime()))) {
                    if (!commitsByCreatedTime.keySet().contains(datePeriod)) {
                        commitsByCreatedTime.put(datePeriod, new ArrayList<>());
                    }
                    commitsByCreatedTime.get(datePeriod).add(commit);
                }
            }
        }
        return commitsByCreatedTime;
    }

    @Override
    public void makeListAndWriteToFile(String folderPath, List<DatePeriod> datePeriodList) {

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        Map<DatePeriod, List<CommitInfo>> dividedCommitsMap = getIssueGroupByTime(datePeriodList);
        for (DatePeriod datePeriod : dividedCommitsMap.keySet()) {
            String fileName = datePeriod.getSinceUntilFileName("issuesList", ".txt");
            File f = new File(folderPath + File.separator + fileName);
            if (f.exists()) {
                f.delete();
            }
            List<CommitInfo> issueInfoList = dividedCommitsMap.get(datePeriod);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                for (CommitInfo commitInfo : issueInfoList) {
                    bw.write(commitInfo.getUrl() + "\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
