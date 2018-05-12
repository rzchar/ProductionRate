package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.gitCommit.GitCommitFileInfo;

import java.util.Set;

/**
 * Created by qyd on 2018/5/11.
 */
public class FileMarker {
    public void markTag(String FileName, String content, String commitMessage){

    }

    static AbstractFilePattern[] patterns = new AbstractFilePattern[]{
            new AbstractFilePattern(CostType.traditionalCodeCost, EffortType.traditionalCode) {
                @Override
                public boolean isThisType(String FileName, String content, String commitMessage, Set<String> currentTag) {
                    return false;
                }
            }
    };
}
