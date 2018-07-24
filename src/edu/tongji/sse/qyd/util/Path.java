package edu.tongji.sse.qyd.util;

import java.io.File;

/**
 * Created by qyd on 2018/4/28.
 */
public class Path {

    static public final File emptyFile = new File("");

    static public final String projectRoot = emptyFile.getAbsolutePath();

    static public final String dataRoot = projectRoot + File.separator + "web" + File.separator + "data";

    static public String getInputPath() {
        return dataRoot + File.separator + Util.getInstance().getProjectFolderName() + File.separator + "input";
    }

    static public String getMiddleDataPath() {
        return dataRoot + File.separator + Util.getInstance().getProjectFolderName() + File.separator + "middle";
    }

    static public String getOutputPath() {
        return dataRoot + File.separator + Util.getInstance().getProjectFolderName() + File.separator + "output";
    }

    public static final String commitFileSuffix = ".json";
    public static final String issueFileSuffix = ".json";

    static public void main(String[] args) {
//        System.out.println(inputPath);
//        System.out.println(getMiddleDataPath());
//        System.out.println(getOutputPath());

        String commitURL = "https://api.github.com/repos/eclipse/che/commits/d879c3faf2e601e24bda50e48222a019107a5333";
        // System.out.println(getEntityHashFromURL(commitURL));
    }


}
