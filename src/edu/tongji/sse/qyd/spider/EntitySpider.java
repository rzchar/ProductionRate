package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.ConnectionAssistant;
import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.gitCommit.GitCommitInfo;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by qyd on 2018/5/8.
 */
abstract public class EntitySpider<T> {

    abstract protected T makeEntityInfoFromResponseContent(String responseContent);

    protected String getEntityContentFromRequest(String urlString) {
        String responseContent = "";

        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
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
            String line = null;
            while ((line = br.readLine()) != null) {
                responseContent += line + "\n";
            }
            //System.out.println(responseContent);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("uncaught exception");
            e.printStackTrace();
        }
        return responseContent;
    }

    protected abstract String getCommitHashFromURL(String url);

    protected abstract String getFileNameFromHash(String hash);

    public T getEntityInfo(String urlString) {
        String commitHash = getCommitHashFromURL(urlString);
        String commitFileName = getFileNameFromHash(commitHash);
        File commitInfoFile = new File(commitFileName);

        String commitContentString = "";
        String line = null;
        if (commitInfoFile.exists()) {
            System.out.println("get entity from file " + commitHash);
            try {
                BufferedReader br = new BufferedReader(new FileReader(commitInfoFile));
                while ((line = br.readLine()) != null) {
                    commitContentString += line;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("get entity from github " + commitHash);
            commitContentString = getEntityContentFromRequest(urlString);
            try {
                commitInfoFile.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(commitInfoFile));
                bw.write(commitContentString);
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return makeEntityInfoFromResponseContent(commitContentString);
    }

    protected abstract String  getEntityListFileName();

    public void getAllEntity() {
        try {
            File commitList = new File(getEntityListFileName());
            BufferedReader br = new BufferedReader(new FileReader(commitList));
            String line = null;
            while ((line = br.readLine()) != null) {
                CommitSpider.getInstance().getEntityInfo(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
