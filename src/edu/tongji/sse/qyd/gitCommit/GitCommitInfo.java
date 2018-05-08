package edu.tongji.sse.qyd.gitCommit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/7.
 */
public class GitCommitInfo {
    static private Set<String> blockedFileSuffix = new HashSet<>();

    {
        blockedFileSuffix.add(".java");
        blockedFileSuffix.add(".xml");
        blockedFileSuffix.add(".html");
    }

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

    public int sumChanges(Pattern pattern) {
        int sum = 0;
        for (int i = 0; i < files.size(); i++) {
            GitCommitFileInfo gitCommitFileInfo = files.get(i);
            Matcher matcher = pattern.matcher(gitCommitFileInfo.getFileName());
            if (matcher.find()) {
                sum += gitCommitFileInfo.getChangeNum();
            }
        }
        return sum;
    }

    public Set<String> getFileTypes() {
        Set<String> fileTypes = new HashSet<String>();
        for (int i = 0; i < files.size(); i++) {
            String fileFullName = files.get(i).getFileName();
            String fileSuffixToAdd = "";
            if (fileFullName.contains(".") && fileFullName.contains("/")) {
                fileSuffixToAdd = fileFullName.substring(
                        Math.max(fileFullName.lastIndexOf("."), fileFullName.lastIndexOf("/")));
            } else if (fileFullName.contains(".")) {
                fileSuffixToAdd = fileFullName.substring(fileFullName.lastIndexOf("."));
            } else if (fileFullName.contains("/")) {
                fileSuffixToAdd = fileFullName.substring(fileFullName.lastIndexOf("/"));
            } else {
                fileSuffixToAdd = fileFullName;
            }
            if (!blockedFileSuffix.contains(fileSuffixToAdd)) {
                fileTypes.add(fileSuffixToAdd);
            }
        }
        return fileTypes;
    }

    public void printFileTypes() {
        Set<String> fileTypes = this.getFileTypes();
        if (fileTypes.size() == 0) {
            return;
        }
        for (String str : fileTypes) {

            System.out.print(str + "; ");
        }
        System.out.println();
    }

    public static void main(String[] a) {
        String fileFullName = "assembly/assembly-wsmaster-war/src/main/webapp/WEB-INF/classes/codenvy/che.properties";
        if (fileFullName.contains(".")) {
            System.out.println(fileFullName.substring(fileFullName.lastIndexOf(".")));
        }
    }
}
