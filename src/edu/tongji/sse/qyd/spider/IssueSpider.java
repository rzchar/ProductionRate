package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.util.Util;
import edu.tongji.sse.qyd.model.IssueInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/8.
 */
public class IssueSpider extends EntitySpider<IssueInfo> {
    static private final Pattern issueURLEnding = Pattern.compile("^https\\://api\\.github\\.com/repos/.*/issues/([0-9]{1,5})$");

    protected static IssueSpider issueSpider = new IssueSpider();

    public static EntitySpider getInstance() {
        return issueSpider;
    }

    public static void main(String[] args) {
        getInstance().getAllEntity(Path.issueListFileName);
    }

    @Override
    protected IssueInfo makeEntityInfoFromResponseContent(String responseContent) {
        try {

            JSONObject issue = new JSONObject(responseContent);
            IssueInfo issueInfo = new IssueInfo(issue.getInt("number"));
            if (issue.has("created_at") && !issue.isNull("created_at")) {
                issueInfo.setCreatedAt(DatePeriod.getDateFromISO8601(issue.getString("created_at")));
            }
            if (issue.has("closed_at") && !issue.isNull("closed_at")) {
                issueInfo.setClosedAt(DatePeriod.getDateFromISO8601(issue.getString("closed_at")));
            }
            issueInfo.setUrl(issue.getString("url"));
            return issueInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            Util.log(this.getClass(), responseContent);
        }
        return null;
    }

    @Override
    protected String getEntityHashFromURL(String url) {
        String result = "";
        Matcher matcher = issueURLEnding.matcher(url);
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    @Override
    protected String getFileNameFromHash(String hash) {
        return Path.getMiddleDataPath() + File.separator + "issues"
                + File.separator + hash + Path.issueFileSuffix;
    }

    @Override
    protected String getEntityListAbsoluteFileName(String issueListName) {
        return Path.getMiddleDataPath() + File.separator + "issueGroupsAll" + File.separator + issueListName;
    }
}
