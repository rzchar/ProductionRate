package edu.tongji.sse.qyd.gitCommit;

import java.util.List;

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
}
