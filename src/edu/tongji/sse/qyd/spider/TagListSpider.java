package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by qyd on 2018/5/4.
 */
public class TagListSpider extends ListSpider {

    public TagListSpider() {
        super();
        this.startURL = URLOfBasicAPI.tags;
        commitListFileName = Path.middleDataPath + File.separator + "tags"
                + File.separator + "#tagList" + ".txt";
        makeNewEmptyFile();
    }

    @Override
    protected void writeContentToFile(String responseContent) {
        if (bw == null) {
            return;
        }
        try {
            JSONArray entityArray = new JSONArray(responseContent);
            for (int i = 0; i < entityArray.length(); i++) {
                JSONObject entityOBJ = entityArray.getJSONObject(i);
                entityOBJ.remove("zipball_url");
                entityOBJ.remove("tarball_url");

                this.bw.write(entityOBJ.toString()+"\n");
                this.bw.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] a) {
        TagListSpider tls = new TagListSpider();
        tls.getListToFile();
    }
}
