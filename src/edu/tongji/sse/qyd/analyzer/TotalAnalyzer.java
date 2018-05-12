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
public class TotalAnalyzer {

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


        }

    }

    public static void main(String[] args) {
        TotalAnalyzer an = new TotalAnalyzer();
        an.calculateProductionAndCost();
    }
}