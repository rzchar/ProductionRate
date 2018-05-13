package edu.tongji.sse.qyd.costAndEffortType;

import edu.tongji.sse.qyd.costAndEffortType.CostType;
import edu.tongji.sse.qyd.costAndEffortType.EffortType;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/13.
 */
public class BasicFilePattern {
    protected CostType costType;

    public CostType getCostType() {
        return this.costType;
    }

    ;

    protected EffortType effortType;

    public EffortType getEffortType() {
        return this.effortType;
    }

    ;

    protected Pattern fileNamePattern;

    protected Pattern contentPattern;

    public BasicFilePattern(CostType c, EffortType e) {
        this.costType = c;
        this.effortType = e;
        this.fileNamePattern = Pattern.compile("");
        this.contentPattern = Pattern.compile("");
    }

    public BasicFilePattern(CostType c, EffortType e, String fileNamePattern) {
        this.costType = c;
        this.effortType = e;
        this.fileNamePattern = Pattern.compile(fileNamePattern);
        this.contentPattern = Pattern.compile("");
    }

    public BasicFilePattern(CostType c, EffortType e, String fileNamePattern, String contentPattern) {
        this.costType = c;
        this.effortType = e;
        this.fileNamePattern = Pattern.compile(fileNamePattern);
        this.contentPattern = Pattern.compile(contentPattern);
    }

    public boolean isThisType(String fileName, String content, String commitMessage, Set<String> currentTag) {
        return isFileNamePatternMatched(fileName) && isContentPatternMatched(content);
    }

    protected boolean isFileNamePatternMatched(String fileName) {
        return fileNamePattern.matcher(fileName).find();
    }

    protected boolean isContentPatternMatched(String content) {
        return contentPattern.matcher(content).find();
    }
}
