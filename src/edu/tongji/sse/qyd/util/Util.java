package edu.tongji.sse.qyd.util;

import edu.tongji.sse.qyd.analyzer.CommitSinglePeriodAnalyzer;
import edu.tongji.sse.qyd.model.Project;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.*;

/**
 * Created by qyd on 2018/5/7.
 */
public class Util {
    public static void log(Class c, String message) {
        if(c != CommitSinglePeriodAnalyzer.class && c!= Util.class){
            return;
        }
        System.out.println("[" + c.getName() + "]" + message);
    }
    private static String commitListFileName = "#commitList.txt";


    public static void main(String[] args) {

        //String[] projects = new String[]{"che", "atom", "ic", "brkt", "msvs"}; ic unavailable
        //String[] projects = new String[]{"che"};
        File log = new File(Path.dataRoot + File.separator + "log.txt");
        Util.makeNewEmptyFile(log);
        PrintStream ps =null ;
//        try {
//            ps= new PrintStream(log);
//            System.setOut(ps);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        String[] projects = new String[]{"che", "atom", "brkt", "msvs"};
        projects = new String[]{"che"};
        for (String pro : projects) {
            Util.log(Util.class, pro +" start");
            Project.setInstance(pro);
            Project currentProject = Project.getInstance();
            //currentProject.fetchListsFromGithub();
            //currentProject.fetchInfoFromGithub();
            //currentProject.groupTheInfo();
            currentProject.analyze();
            //currentProject.writeMatlabScript();
            Util.log(Util.class, pro +" end");
        }
        if(ps != null){ps.close();}
    }

    static public void makeParentDir(File f) {
        File parent = f.getParentFile();
        if (!(parent.isDirectory() && parent.exists())) {
            parent.mkdirs();
        }
    }

    static public void makeNewEmptyFile(File f){
        makeParentDir(f);
        if(f.exists()){
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
