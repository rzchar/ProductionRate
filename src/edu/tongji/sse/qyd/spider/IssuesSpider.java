package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.gitIssue.IssueInfo;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/8.
 */
public class IssuesSpider extends EntitySpider<IssueInfo> {
    @Override
    protected IssueInfo makeEntityInfoFromResponseContent(String responseContent) {
        return null;
    }

    static private final Pattern issueURLEnding = Pattern.compile("^https\\://api\\.github\\.com/repos/.*/commits/([0-9]{1,5})$");

    @Override
    protected String getCommitHashFromURL(String url) {
        String result = "";
        Matcher matcher = issueURLEnding.matcher(url);
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    @Override
    protected String getFileNameFromHash(String hash) {
        return Path.middleDataPath + File.separator + "issues"
                + File.separator + hash + Path.issueFileSuffix;
    }

    @Override
    protected String getEntityListFileName() {
        return Path.middleDataPath + File.separator + "issueGroups" + File.separator + "#issueListAll.txt";
    }

    protected static IssuesSpider issuesSpider = new IssuesSpider();

    public static IssuesSpider getInstance(){
        return issuesSpider;
    }

    public static void main(String[] args) {
        getInstance().getAllEntity();
    }
}
