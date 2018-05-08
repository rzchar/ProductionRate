package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.Path;
import edu.tongji.sse.qyd.gitCommit.GitCommitInfo;
import edu.tongji.sse.qyd.spider.CommitSpider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/8.
 */
public class Analyzer {

    private List<List<String>> getCommitURLListByWeek() {
        List<List<String>> commitListByWeeks = new ArrayList<List<String>>();
        String folderPath = Path.middleDataPath + File.separator + "commitGroups" + File.separator + "byWeekExample1";
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return commitListByWeeks;
        }
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            //System.out.println(f.getName());
            List<String> urls = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = "";
                while ((line = br.readLine()) != null) {
                    if (!line.equals("")) {
                        urls.add(line);
                        //System.out.println(line);
                    }
                }
                commitListByWeeks.add(urls);
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return commitListByWeeks;
    }

    public void calculateProductionAndCost() {
        List<List<String>> commitListByWeeks = this.getCommitURLListByWeek();
        for (int iweeks = 0; iweeks < commitListByWeeks.size(); iweeks++) {
            List<String> urls = commitListByWeeks.get(iweeks);

            int[] sum = new int[]{0, 0, 0, 0};
            Pattern[] patterns = new Pattern[]{Pattern.compile(""), Pattern.compile("\\.sh$"), Pattern.compile("\\.java$"),
                    Pattern.compile("\\.mvn$")};
            Set<String> authors = new HashSet<>();
            //Set<String> types = new HashSet<>();

            for (int iurl = 0; iurl < urls.size(); iurl++) {
                String url = urls.get(iurl);
                GitCommitInfo gitCommitInfo = CommitSpider.getGitCommitInfo(url);
                for (int ipatterns = 0; ipatterns < patterns.length; ipatterns++) {
                    sum[ipatterns] += gitCommitInfo.sumChanges(patterns[ipatterns]);
                }
                authors.add(gitCommitInfo.getAuthorId());

            }
            System.out.println("week:" + iweeks + ";" + "authors number:" + authors.size() + ";");
            for (int ipatterns = 0; ipatterns < patterns.length; ipatterns++) {
                System.out.print(sum[ipatterns] + ";");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        Analyzer an = new Analyzer();
        an.calculateProductionAndCost();
    }
}
