package edu.tongji.sse.qyd.analyzer;

import java.util.Set;

/**
 * Created by qyd on 2018/5/13.
 */
public abstract class AbstractFilePattern {
    CostType costType;

    public CostType getCostType(){
        return this.costType;
    };

    EffortType effortType;

    public EffortType getEffortType(){
        return this.effortType;
    };

    public AbstractFilePattern(CostType c,EffortType e){
        this.costType = c;
        this.effortType = e;
    }

    abstract public boolean isThisType(String FileName, String content, String commitMessage, Set<String> currentTag);
}
