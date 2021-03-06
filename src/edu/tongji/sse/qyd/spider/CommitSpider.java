package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.model.GitCommitFileInfo;
import edu.tongji.sse.qyd.model.CommitInfo;
import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Get Commit from file or
 * Created by qyd on 2018/4/24.
 */
public class CommitSpider extends EntitySpider<CommitInfo> {

    static private final Pattern commitURLEnding = Pattern.compile("^https://api\\.github\\.com/repos/.*/commits/([0-9a-fA-F]{40})$");
    protected static CommitSpider commitSpider = new CommitSpider();

    public static CommitSpider getInstance() {
        return commitSpider;
    }

    public static void main(String[] args) {
        //getGitCommitFileInfoList("https://api.github.com/repos/eclipse/che");
        //getInstance().getEntityInfo("https://api.github.com/repos/eclipse/che/commits/4b39f7f581f7816695629f13a604923a80395b72");

        getInstance().getAllEntity("#commitList.txt");
//        getInstance().getEntityInfo("https://api.github.com/repos/eclipse/che/commits/0a3ad3d184f9888a610839e838274b43ad9dd7c4");
//        getInstance().getEntityInfo("https://api.github.com/repos/eclipse/che/commits/6c96974d4640a773d8f37d46b08e93ae5b0f7406");
//        getInstance().getEntityInfo("https://api.github.com/repos/eclipse/che/commits/3ed366b74f5a4149cc6e516fcad82910e402689c");
    }

    @Override
    protected CommitInfo makeEntityInfoFromResponseContent(String responseContent) {
        List<GitCommitFileInfo> fileInfoList = new ArrayList<>();

        JSONObject commit = new JSONObject(responseContent);
        if (!commit.has("sha")) {
            System.out.println("A Commit Without Sha");
            return null;
        }
        String commitHash = commit.getString("sha");
        if (commit.has("files")) {
            JSONArray files = commit.getJSONArray("files");
            for (int i = 0; i < files.length(); i++) {
                try {
                    JSONObject fileObject = files.getJSONObject(i);
                    String status = fileObject.getString("status");
                    String patch = "";
                    if (fileObject.has("patch")) {
                        patch = fileObject.getString("patch");
                    }
                    fileInfoList.add(new GitCommitFileInfo(
                            commitHash,
                            fileObject.getInt("additions"),
                            fileObject.getInt("deletions"),
                            fileObject.getInt("changes"),
                            fileObject.getString("filename"),
                            status,
                            patch
                    ));
                } catch (JSONException e) {
                    System.out.println(responseContent);
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

        String authorId = getAuthorOrCommitterInfo(commit.getJSONObject("commit"), "name");
        authorId = authorId.equals("") ? getAuthorOrCommitterInfo(commit.getJSONObject("commit"), "email") : authorId;
        String time = getAuthorOrCommitterInfo(commit.getJSONObject("commit"), "date");
        String url = commit.getString("url");
        return new CommitInfo(url, authorId, time, fileInfoList);
    }

    @Override
    protected String getEntityHashFromURL(String url) {
        String result = "";
        Matcher matcher = commitURLEnding.matcher(url);
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    protected String getFileNameFromHash(String hash) {
        return Path.getMiddleDataPath() + File.separator + "commits"
                + File.separator + hash + Path.commitFileSuffix;
    }

    @Override
    protected String getEntityListAbsoluteFileName(String commitListName) {
        return Path.getMiddleDataPath() + File.separator + "commitGroups" + File.separator + commitListName;
    }

    private String getAuthorOrCommitterInfo(JSONObject jo, String key) {
        String value = "";
        JSONObject authorOrCommitter = new JSONObject();
        authorOrCommitter.append(key, "");
        if (jo.has("author") && !jo.isNull("author")) {
            authorOrCommitter = jo.getJSONObject("author");
            if (authorOrCommitter.has(key) && !authorOrCommitter.isNull(key)) {
                return authorOrCommitter.getString(key);
            }
        }
        if (jo.has("committer") && !jo.isNull("committer")) {
            authorOrCommitter = jo.getJSONObject("committer");
            if (authorOrCommitter.has(key) && !authorOrCommitter.isNull(key)) {
                return authorOrCommitter.getString(key);
            }
        }
        Util.log(this.getClass(), "get nothing for [" + key + "]" + jo.toString());
        return "";
    }
}
