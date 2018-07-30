package edu.tongji.sse.qyd.util;

import edu.tongji.sse.qyd.model.Project;

import java.io.File;

/**
 * Created by qyd on 2018/5/7.
 */
public class Util {


    public static void log(Class c, String message) {
        System.out.println("[" + c.getName() + "]" + message);
    }

    private static String commitListFileName = "#commitList.txt";


    public static void main(String[] args) {

        //String[] projects = new String[]{"che", "atom", "ic", "brkt", "msvs"}; ic unavailable
        String[] projects = new String[]{"che", "atom", "brkt", "msvs"};
        for (String pro : projects) {
            Util.log(Util.class, pro +" start");
            Project.setInstance(pro);
            Project currentProject = Project.getInstance();
            //currentProject.fetchListsFromGithub();
            //currentProject.fetchInfoFromGithub();
            //currentProject.groupTheInfo();
            currentProject.analyze();
            Util.log(Util.class, pro +" end");
        }
    }

    static public void makeParentDir(File f) {
        File parent = f.getParentFile();
        if (!(parent.isDirectory() && parent.exists())) {
            parent.mkdirs();
        }
    }
}
