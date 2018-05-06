package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.util.Date;

/**
 * Created by qyd on 2018/5/6.
 */
public class CommitGroupSpider {
    private class WeekCommitListSpider extends CommitListSpider {
        public WeekCommitListSpider() {
            super();
            this.startURL = URLOfBasicAPI.commits;
            commitListFileName = Path.middleDataPath + File.separator + "commits"
                    + File.separator + "#commitList" + (new Date()).getTime() + ".txt";
            makeNewEmptyFile();
        }

        @Override
        protected void addURLParam(HttpsURLConnection connection) {
        }
    }
}
