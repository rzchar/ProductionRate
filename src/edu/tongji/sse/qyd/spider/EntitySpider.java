package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.ConnectionAssistant;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * To get Entity in one Subject
 * Entity can be a commit or a  Issue by now
 * Created by qyd on 2018/5/8.
 */
abstract public class EntitySpider<T> {

    abstract protected T makeEntityInfoFromResponseContent(String responseContent);

    protected String getEntityContentFromRequest(String urlString) {
        String responseContent = "";

        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            ConnectionAssistant.addAuthority(connection);
            connection.connect();
            //connection.getInputStream();

            //System.out.println("response code: " + connection.getResponseCode());
            System.out.println("x-ratelimit-remaining: " + connection.getHeaderField("x-ratelimit-remaining"));

            Thread.sleep(700L);
            if (connection.getHeaderFieldInt("x-ratelimit-remaining", 5000) < 100) {
                Thread.sleep(3700L * 1000L);
            }

            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                responseContent += line + "\n";
            }
            //System.out.println(responseContent);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("uncaught exception");
            e.printStackTrace();
        }
        return responseContent;
    }

    protected abstract String getEntityHashFromURL(String url);

    protected abstract String getFileNameFromHash(String hash);

    public T getEntityInfo(String urlString) {
        String entityHash = getEntityHashFromURL(urlString);
        String entityFileName = getFileNameFromHash(entityHash);
        File entityInfoFile = new File(entityFileName);

        String commitContentString = "";
        String line = null;
        if (entityInfoFile.exists()) {
            //System.out.println("get entity from file " + entityHash);
            try {
                BufferedReader br = new BufferedReader(new FileReader(entityInfoFile));
                while ((line = br.readLine()) != null) {
                    commitContentString += line;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("get entity from github " + entityHash);
            commitContentString = getEntityContentFromRequest(urlString);
            if(!commitContentString.equals("")) {
                try {
                    entityInfoFile.createNewFile();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(entityInfoFile));
                    bw.write(commitContentString);
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return makeEntityInfoFromResponseContent(commitContentString);
    }

    protected abstract String getEntityListFileName();

    public void getAllEntity() {
        try {
            File entityList = new File(getEntityListFileName());
            BufferedReader br = new BufferedReader(new FileReader(entityList));
            String line;
            while ((line = br.readLine()) != null) {
                getEntityInfo(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
