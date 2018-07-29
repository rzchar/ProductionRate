package edu.tongji.sse.qyd.model;

/**
 * Created by qyd on 2018/4/24.
 */
public class GitCommitFileInfo {
    private String gitHash;
    private int additionNum;
    private int deletionNum;
    private int changeNum;
    private String fileName;
    private String patch;
    private String status;

    public GitCommitFileInfo() {
        this.gitHash = "";
        this.additionNum = 0;
        this.deletionNum = 0;
        this.changeNum = 0;
        this.fileName = "";
        this.patch = "";
        this.status = "";
    }

    public GitCommitFileInfo(String gitHash, int additionNum, int deletionNum, int changeNum, String fileName, String status, String patch) {
        this.gitHash = gitHash;
        this.additionNum = additionNum;
        this.deletionNum = deletionNum;
        this.changeNum = changeNum;
        this.fileName = fileName;
        this.status = status;
        this.patch = patch;
    }

    public String getGitHash() {
        return gitHash;
    }

    public void setGitHash(String gitHash) {
        this.gitHash = gitHash;
    }

    public String getPatch() {
        return patch;
    }

    public boolean hasTag(String tag) {
        return patch.contains(tag);
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
}
