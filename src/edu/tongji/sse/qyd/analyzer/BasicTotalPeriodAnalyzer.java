package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;
import edu.tongji.sse.qyd.Util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qyd on 2018/5/17.
 */
abstract public class BasicTotalPeriodAnalyzer<T extends BasicSinglePeriodAnalyzer> {

    protected List<T> getAnalyzerByPeroid(String groupFolderPath, List<DatePeriod> datePeriodList) {
        List<T> basicSinglePeriodAnalyzerArrayList = new ArrayList<T>();

        //File[] files = folder.listFiles();
        for (int i = 0; i < datePeriodList.size(); i++) {
            File f = new File(getFileFullName(groupFolderPath, datePeriodList.get(i)));
            List<String> urls = new ArrayList<>();

            if (!f.exists()) {
                Util.log(this.getClass(), "file doesn't exist: " + f.getAbsolutePath() + ", add empty analyzer.");
            } else {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.equals("")) {
                            urls.add(line);
                            //System.out.println(line);
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(f.getName());
            T someSinglePeriodAnalyzer = null;
            try {
                someSinglePeriodAnalyzer = getSingleEntityType().getConstructor(DatePeriod.class, List.class).newInstance(datePeriodList.get(i), urls);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            basicSinglePeriodAnalyzerArrayList.add(someSinglePeriodAnalyzer);
        }
        return basicSinglePeriodAnalyzerArrayList;
    }

    abstract protected String getFileFullName(String groupFolderPath, DatePeriod datePeriod);

    abstract protected Class<T> getSingleEntityType();
}
