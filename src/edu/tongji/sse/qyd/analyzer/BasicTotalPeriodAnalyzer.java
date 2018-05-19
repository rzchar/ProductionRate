package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.Util.DatePeriod;

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
            if (!f.exists()) {
                System.out.println("file doesn't exist: " + f.getAbsolutePath());
                continue;
            }
            System.out.println(f.getName());
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
                T newInstance = getSingleEntityType().getConstructor(DatePeriod.class, List.class).newInstance(datePeriodList.get(i), urls);
                basicSinglePeriodAnalyzerArrayList.add(newInstance);
                br.close();
            } catch (IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return basicSinglePeriodAnalyzerArrayList;
    }

    abstract protected String getFileFullName(String groupFolderPath, DatePeriod datePeriod);

    abstract protected Class<T> getSingleEntityType();
}
