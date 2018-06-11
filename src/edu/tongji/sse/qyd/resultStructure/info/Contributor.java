package edu.tongji.sse.qyd.resultStructure.info;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by qyd on 2018/6/10.
 */
public class Contributor {

    private static Map<String, Integer> contributeRecord = new HashMap<>();

    private static void addContributeRecord(String authorName) {
        if (!contributeRecord.keySet().contains(authorName)) {
            contributeRecord.put(authorName, new Integer(0));
        }
        contributeRecord.put(authorName, contributeRecord.get(authorName).intValue() + 1);
    }

    public static void clearContributeRecord(){
        contributeRecord.clear();
    }

    private Set<String> authors = new HashSet<>();

    public void clearAuthors(){
        authors.clear();
    }

    public void addAuthor(String authorName){
        authors.add(authorName);
        addContributeRecord(authorName);
    }

    public int getAuthorAmount(){
        return authors.size();
    }
}
