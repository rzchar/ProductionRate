package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;
import edu.tongji.sse.qyd.Util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by qyd on 2018/5/4.
 */
public class TagListSpider extends ListSpider {

    public TagListSpider() {
        super();
        this.startURL = URLOfBasicAPI.tags;
        listFileName = Path.middleDataPath + File.separator + "tags"
                + File.separator + "#tagList" + ".txt";
        makeNewEmptyFile();
    }

    public static void main(String[] a) {
        TagListSpider tls = new TagListSpider();
        tls.getListToFile();
    }

    @Override
    protected void writeContentToFile(String responseContent) {
        if (bw == null) {
            return;
        }
        try {
            CommitSpider commitSpider = new CommitSpider();
            JSONArray entityArray = new JSONArray(responseContent);
            for (int i = 0; i < entityArray.length(); i++) {
                JSONObject entityOBJ = entityArray.getJSONObject(i);
                entityOBJ.remove("zipball_url");
                entityOBJ.remove("tarball_url");
                String commitURL = entityOBJ.getJSONObject("commit").getString("url");
                JSONObject commitObj = new JSONObject(commitSpider.getEntityContentFromRequest(commitURL));
                String commitTime = commitObj.getJSONObject("commit").getJSONObject("committer").getString("date");
                String name = entityOBJ.getString("name");
                Util.log(this.getClass(), name + "  " + commitTime);
                this.bw.write(entityOBJ.toString() + "\n");

                this.bw.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
