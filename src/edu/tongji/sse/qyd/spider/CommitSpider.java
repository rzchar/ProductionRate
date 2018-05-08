package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.ConnectionAssistant;
import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.gitCommit.GitCommitFileInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qyd on 2018/4/24.
 */
public class CommitSpider {

    private static String getCommitContentFromRequest(String urlString) {
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
            if(connection.getHeaderFieldInt("x-ratelimit-remaining",5000)<100){
                Thread.sleep(3700L*1000L);
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
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return responseContent;
    }

    private static List<GitCommitFileInfo> pickFilesInCommit(String responseContent) {
        List<GitCommitFileInfo> result = new ArrayList<GitCommitFileInfo>();
        JSONObject commit = new JSONObject(responseContent);
        if (commit.has("files")) {
            JSONArray files = commit.getJSONArray("files");
            for (int i = 0; i < files.length(); i++) {
                JSONObject fileObject = files.getJSONObject(i);
                result.add(new GitCommitFileInfo(
                        fileObject.getInt("additions"),
                        fileObject.getInt("deletions"),
                        fileObject.getInt("changes"),
                        fileObject.getString("filename")
                ));
            }
        }
        return result;
    }

    public static List<GitCommitFileInfo> getGitCommitFileInfoList(String urlString) {
        String commitHash = Path.getCommitHashFromURL(urlString);
        String commitFileName = Path.middleDataPath + File.separator + "commits"
                + File.separator + commitHash + Path.commitFileSuffix;
        File commitInfoFile = new File(commitFileName);

        String commitContentString = "";
        String line = null;
        if (commitInfoFile.exists()) {
            System.out.println("get commit from file " + commitHash);
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
            System.out.println("get commit from github " + commitHash);
            commitContentString = getCommitContentFromRequest(urlString);
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
        return pickFilesInCommit(commitContentString);
    }

    public static void getAllCommits(){
        try{
            File commitList = new File(Path.middleDataPath + File.separator + "commits" + File.separator + "#commitList.txt");
            BufferedReader br =  new BufferedReader(new FileReader(commitList));
            String line = null;
            while((line = br.readLine())!=null){
                getGitCommitFileInfoList(line);
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //getGitCommitFileInfoList("https://api.github.com/repos/eclipse/che");
        //getCommitContentFromRequest("https://api.github.com/repos/eclipse/che/commits/d879c3faf2e601e24bda50e48222a019107a5333");
        getGitCommitFileInfoList("https://api.github.com/repos/eclipse/che/commits/d879c3faf2e601e24bda50e48222a019107a5333");
        getGitCommitFileInfoList("https://api.github.com/repos/eclipse/che/commits/6c96974d4640a773d8f37d46b08e93ae5b0f7406");
        getGitCommitFileInfoList("https://api.github.com/repos/eclipse/che/commits/3ed366b74f5a4149cc6e516fcad82910e402689c");


    }
}
