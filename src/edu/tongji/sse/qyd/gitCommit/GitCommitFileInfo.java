package edu.tongji.sse.qyd.gitCommit;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by qyd on 2018/4/24.
 */
public class GitCommitFileInfo {
    private String gitHash;
    private int additionNum;
    private int deletionNum;
    private int changeNum;
    private String fileName;
    private Set<String> tags;
    private String status;

    public String getGitHash() {
        return gitHash;
    }

    public void setGitHash(String gitHash) {
        this.gitHash = gitHash;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAdditionNum() {
        return additionNum;
    }

    public void setAdditionNum(int additionNum) {
        this.additionNum = additionNum;
    }

    public int getDeletionNum() {
        return deletionNum;
    }

    public void setDeletionNum(int deletionNum) {
        this.deletionNum = deletionNum;
    }

    public int getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(int changeNum) {
        this.changeNum = changeNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public GitCommitFileInfo() {
        this.gitHash = "";
        this.additionNum = 0;
        this.deletionNum = 0;
        this.changeNum = 0;
        this.fileName = "";
        this.tags = new HashSet<String>();
        this.status = "";
    }

    public GitCommitFileInfo(String gitHash, int additionNum, int deletionNum, int changeNum, String fileName, String status) {
        this.gitHash = gitHash;
        this.additionNum = additionNum;
        this.deletionNum = deletionNum;
        this.changeNum = changeNum;
        this.fileName = fileName;
        this.status = status;
        this.tags = new HashSet<>();
    }
}
