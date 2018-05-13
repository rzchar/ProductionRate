package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.Path;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void calculateEffortAndCost() {
        List<List<String>> commitListByWeeks = this.getCommitURLListByWeek();
        for (int iweeks = 0; iweeks < commitListByWeeks.size(); iweeks++) {
            List<String> urls = commitListByWeeks.get(iweeks);
            System.out.println("week " + iweeks);
            for(String commits : urls){
                System.out.println(urls);
            }

        }

    }

    public static void main(String[] args) {
        TotalAnalyzer an = new TotalAnalyzer();
        an.calculateEffortAndCost();
    }
}
