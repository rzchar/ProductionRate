package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.util.ConnectionAssistant;
import edu.tongji.sse.qyd.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/4.
 */
public abstract class ListSpider {
    protected BufferedWriter bw = null;
    protected Pattern nextLinkPattern = Pattern.compile("<(.*)>;\\srel=\"next\"");

    protected String startURL;
    protected String listFileName;

    protected ListSpider() {
        this.startURL = "";
    }

    public ListSpider(String startURL, String fileName) {
        this.startURL = startURL;
        this.listFileName = fileName;
    }

    protected void makeNewEmptyFile() {
        File commitListFile = new File(listFileName);
        if (commitListFile.exists()) {
            commitListFile.delete();
        }
        try {
            commitListFile.getParentFile().mkdirs();
            commitListFile.createNewFile();
            this.bw = new BufferedWriter(new FileWriter(commitListFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() {
        if (this.bw != null) {
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getNextLink(String[] links) {
        for (int i = 0; i < links.length; i++) {
            Matcher m = nextLinkPattern.matcher(links[i]);
            if (m.find()) {
                return m.group(1);
            }
        }
        return "";

    }

    public void getListToFile() {
        String nextPage = this.startURL;
//        int pageLimit = 3;
        while (!nextPage.equals("")) {
            nextPage = this.getListFromRequest(nextPage);
//            pageLimit--;
//            if(pageLimit == 0){
//                break;
//            }
        }

    }

    protected String getListFromRequest(String urlString) {

        String responseContent = "";
        String nextPage = "";
        boolean succeed = false;
        while(!succeed) {
            Util.log(this.getClass(),"urlList:" + urlString);
            try {
                URL url = new URL(urlString);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                ConnectionAssistant.addAuthority(connection);
                connection.connect();

                //get next page
                if (connection.getHeaderField("Link") != null) {
                    String[] links = connection.getHeaderField("Link").split(",");
                    nextPage = getNextLink(links);
                }

                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    responseContent += line + "\n";
                }

                writeContentToFile(responseContent);
                ConnectionAssistant.spiderLimitCheck(connection);
                connection.disconnect();
                succeed = true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nextPage;
    }

    protected void writeContentToFile(String responseContent) {
        if (bw == null) {
            return;
        }
        try {
            JSONArray entityArray = new JSONArray(responseContent);
            for (int i = 0; i < entityArray.length(); i++) {
                JSONObject entityOBJ = entityArray.getJSONObject(i);
                if (entityOBJ.has("url")) {
                    this.bw.write(entityOBJ.getString("url") + "\n");
                    this.bw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
