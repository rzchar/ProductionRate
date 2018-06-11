package edu.tongji.sse.qyd.resultStructure;

import edu.tongji.sse.qyd.resultStructure.cost.CostType;
import edu.tongji.sse.qyd.resultStructure.effort.EffortType;

import java.util.regex.Pattern;

/**
 * Created by qyd on 2018/5/13.
 */
public class BasicFilePattern {
    protected CostType costType;
    protected EffortType effortType;
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

    public CostType getCostType() {
        return this.costType;
    }

    public EffortType getEffortType() {
        return this.effortType;
    }

    public boolean isThisType(String fileName, String content, String commitMessage) {
        return isFileNamePatternMatched(fileName) && isContentPatternMatched(content);
    }

    protected boolean isFileNamePatternMatched(String fileName) {
        return fileNamePattern.matcher(fileName).find();
    }

    protected boolean isContentPatternMatched(String content) {
        return contentPattern.matcher(content).find();
    }
}
