package edu.tongji.sse.qyd.Util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/4/28.
 */
public class Path {

    static public final File emptyFile = new File("");
    static public final String projectRoot = emptyFile.getAbsolutePath();
    static public final String inputPath = projectRoot + File.separator + "web" + File.separator + "input";

    static public final String middleDataPath = projectRoot + File.separator + "web" + File.separator + "middleData";

    static public final String outputPath = projectRoot + File.separator + "web" + File.separator + "output";

    static private final Pattern commitURLEnding = Pattern.compile("^https\\://api\\.github\\.com/repos/.*/commits/([0-9a-fA-F]{40})$");

    static public final String commitFileSuffix = ".json";

    static public String getCommitHashFromURL(String url){
        String result = "";
        Matcher matcher = commitURLEnding.matcher(url);
        if(matcher.find()){
            result = matcher.group(1);
        }
        return result;
    }

    static public void main(String[] args) {
//        System.out.println(inputPath);
//        System.out.println(middleDataPath);
//        System.out.println(outputPath);

        String commitURL = "https://api.github.com/repos/eclipse/che/commits/d879c3faf2e601e24bda50e48222a019107a5333";
        System.out.println(getCommitHashFromURL(commitURL));
    }


}
