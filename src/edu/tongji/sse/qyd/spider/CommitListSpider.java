package edu.tongji.sse.qyd.spider;

import edu.tongji.sse.qyd.Util.ConnectionAssistant;
import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.Util.URLOfBasicAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommitListSpider {

    private int pagesCount = 0;

    private BufferedWriter bw = null;

    private String startURL;

    public CommitListSpider(String url) {
        this.startURL = url;
        String commitListFileName = Path.middleDataPath + File.separator + "commits"
                + File.separator + "#commitList" + (new Date()).getTime() + ".txt";
        File commitListFile = new File(commitListFileName);
        if (commitListFile.exists()) {
            commitListFile.delete();
        }
        try {
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

    private static String testString =
            "<https://api.github.com/repositories/32935745/commits?page=6>; rel=\"next\","
                    + " <https://api.github.com/repositories/32935745/commits?page=204>; rel=\"last\","
                    + " <https://api.github.com/repositories/32935745/commits?page=1>; rel=\"first\","
                    + " <https://api.github.com/repositories/32935745/commits?page=4>; rel=\"prev\"";

    Pattern nextLinkPattern = Pattern.compile("<(.*)>;\\srel=\"next\"");

    //Set<String> urls = new HashSet<>();

    private String getNextLink(String[] links) {
        for (int i = 0; i < links.length; i++) {
            Matcher m = nextLinkPattern.matcher(links[i]);
            if (m.find()) {
                return m.group(1);
            }
        }
        return "";

    }

    public void getCommitListToFile() {
        String nextPage = this.startURL;
//        int pageLimit = 3;
        while(!nextPage.equals("")){
            nextPage  = this.getCommitListFromRequest(nextPage);
//            pageLimit--;
//            if(pageLimit == 0){
//                break;
//            }
        }

    }


    private String getCommitListFromRequest(String urlString) {

        String responseContent = "";
        String nextPage = "";
        System.out.println("urlList:" + urlString);

        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            ConnectionAssistant.addAuthority(connection);
            connection.connect();

            //get next page
            String[] links = connection.getHeaderField("Link").split(",");
            nextPage = getNextLink(links);

            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                responseContent += line + "\n";
            }

            JSONArray commitsArray = new JSONArray(responseContent);
            for (int i = 0; i < commitsArray.length(); i++) {
                JSONObject commitOBJ = commitsArray.getJSONObject(i);
                if (commitOBJ.has("url")) {
                    this.bw.write(commitOBJ.getString("url") + "\n");
                    this.bw.flush();
                }
            }
            //System.out.println(responseContent);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextPage;
    }

    public static void main(String[] args) {
        CommitListSpider commitListSpider = new CommitListSpider(URLOfBasicAPI.commits);
        //System.out.println(commitListSpider.getNextLink(testString.split(",")));
        commitListSpider.getCommitListToFile();
    }
}
