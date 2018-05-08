package edu.tongji.sse.qyd.gitCommit;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/7.
 */
public class GitCommitInfo {
    public List<GitCommitFileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<GitCommitFileInfo> files) {
        this.files = files;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    private List<GitCommitFileInfo> files;
    private String authorId;

    public GitCommitInfo(String authorId, List<GitCommitFileInfo> files) {
        this.files = files;
        this.authorId = authorId;
    }

    public int sumChanges() {
        int sum = 0;
        for (int i = 0; i < files.size(); i++) {
            sum += files.get(i).getChangeNum();
        }
        return sum;
    }

    public int sumChanges(Pattern pattern){
        int sum = 0;
        for (int i = 0; i < files.size(); i++) {
            GitCommitFileInfo gitCommitFileInfo = files.get(i);
            Matcher matcher = pattern.matcher(gitCommitFileInfo.getFileName());
            if(matcher.find()) {
                sum += gitCommitFileInfo.getChangeNum();
            }
        }
        return sum;
    }
}
