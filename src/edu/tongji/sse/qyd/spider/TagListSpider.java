package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;


public class TagListSpider extends ListSpider {

    static boolean simpleMod = true;

    public TagListSpider(String startURL, String fileName) {
        super(startURL, fileName);
        if (!fileName.contains(File.separator)) {
            listFileName = Path.getMiddleDataPath() + File.separator + "tags"
                    + File.separator + fileName;
        }
        makeNewEmptyFile();
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
                if(simpleMod){
                    this.bw.write(name + "  :  " + commitTime + "\n");
                }else {
                    this.bw.write(entityOBJ.toString() + "\n");
                }
                this.bw.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
