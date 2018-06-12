package edu.tongji.sse.qyd.resultStructure.info;

/**
 * Created by qyd on 2018/6/10.
 */
public class InfoSet {
    private int committerAmount = 0;

    public int getCommitterAmount() {
        return committerAmount;
    }

    public void setCommitterAmount(int committerAmount) {
        this.committerAmount = committerAmount;
    }

    public void addCommitterAmount(int committerAmount) {
        this.committerAmount += committerAmount;
    }

    public double[] getInfomation(){
        return new double[]{
                Double.valueOf(committerAmount)
        };
    }

    public static String[] getHeader(){
        return new String[]{
                "CommitterAmount"
        };
    }
}
