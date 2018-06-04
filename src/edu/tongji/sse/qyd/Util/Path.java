package edu.tongji.sse.qyd.Util;

import java.io.File;

/**
 * Created by qyd on 2018/4/28.
 */
public class Path {

    static public String projectName = "che";
    static public final File emptyFile = new File("");
    static public final String projectRoot = emptyFile.getAbsolutePath();
    static public final String inputPath = projectRoot + File.separator + "web" + File.separator + "input";

    static public final String middleDataPath = projectRoot + File.separator + "web" + File.separator + "middleData";

    static public final String outputPath = projectRoot + File.separator + "web" + File.separator + "output";


    public static final String commitFileSuffix = ".json";
    public static final String issueFileSuffix = ".json";

    static public void main(String[] args) {
//        System.out.println(inputPath);
//        System.out.println(middleDataPath);
//        System.out.println(outputPath);

        String commitURL = "https://api.github.com/repos/eclipse/che/commits/d879c3faf2e601e24bda50e48222a019107a5333";
        // System.out.println(getEntityHashFromURL(commitURL));
    }


}
