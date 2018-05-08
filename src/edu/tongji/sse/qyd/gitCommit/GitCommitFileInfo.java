package edu.tongji.sse.qyd.gitCommit;

/**
 * Created by qyd on 2018/4/24.
 */
public class GitCommitFileInfo {
    private int additionNum;
    private int deletionNum;
    private int changeNum;
    private String fileName;

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
        this.additionNum = 0;
        this.deletionNum = 0;
        this.changeNum = 0;
        this.fileName = "";
    }

    public GitCommitFileInfo(int additionNum, int deletionNum, int changeNum, String fileName) {
        this.additionNum = additionNum;
        this.deletionNum = deletionNum;
        this.changeNum = changeNum;
        this.fileName = fileName;
    }
}
