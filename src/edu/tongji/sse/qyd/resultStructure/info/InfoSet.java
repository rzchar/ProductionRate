package edu.tongji.sse.qyd.resultStructure.info;

/**
 * Created by qyd on 2018/6/10.
 */
public class InfoSet {
    private int committerAmount = 0;

    private int tag = 0;

    public int getCommitterAmount() {
        return committerAmount;
    }

    public void setCommitterAmount(int committerAmount) {
        this.committerAmount = committerAmount;
    }

    public void addCommitterAmount(int committerAmount) {
        this.committerAmount += committerAmount;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public double[] getInfomation(){
        return new double[]{
                Double.valueOf(committerAmount),
                Double.valueOf(tag)
        };
    }

    public static String[] getHeader(){
        return new String[]{
                "CommitterAmount",
                "Tag"
        };
    }
}
