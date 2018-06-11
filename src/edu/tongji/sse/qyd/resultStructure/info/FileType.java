package edu.tongji.sse.qyd.resultStructure.info;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by qyd on 2018/6/10.
 */
public class FileType {
    static private Set<String> fileSuffixSet = new HashSet<>();


    private static int fileTypeAdded = 0;

    public static void reset() {
        fileSuffixSet.clear();
    }

    public static void add(String fileSuffix) {
        if(!fileSuffixSet.contains(fileSuffix)){
            fileTypeAdded += 1;
            fileSuffixSet.add(fileSuffix);
        }
    }

    public static void clearFileTypeAdded(){
        fileTypeAdded = 0;
    }

    public static int getFileTypeAdded() {
        return fileTypeAdded;
    }
}
