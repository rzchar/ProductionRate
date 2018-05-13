package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.Path;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TotalAnalyzer {

    public static void main(String[] args) {
        TotalAnalyzer an = new TotalAnalyzer();
        an.calculateEffortAndCost();
    }

    private List<BasicPeriodCommitAnalyzer> getCommitURLListByWeek() {

        List<BasicPeriodCommitAnalyzer> basicPeriodCommitAnalyzers = new ArrayList<BasicPeriodCommitAnalyzer>();

        String folderPath = Path.middleDataPath + File.separator + "commitGroups" + File.separator + "byWeekExample1";

        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return basicPeriodCommitAnalyzers;
        }
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            //System.out.println(f.getName());
            List<String> urls = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.equals("")) {
                        urls.add(line);
                        //System.out.println(line);
                    }
                }
                basicPeriodCommitAnalyzers.add(new BasicPeriodCommitAnalyzer(f.getName(), urls));
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return basicPeriodCommitAnalyzers;
    }

    public List<BasicPeriodCommitAnalyzer> calculateEffortAndCost() {
        List<BasicPeriodCommitAnalyzer> basicPeriodCommitAnalyzers = this.getCommitURLListByWeek();
        for (int iweeks = 0; iweeks < basicPeriodCommitAnalyzers.size(); iweeks++) {
            basicPeriodCommitAnalyzers.get(iweeks).statistic();
        }
        return basicPeriodCommitAnalyzers;
    }
}
