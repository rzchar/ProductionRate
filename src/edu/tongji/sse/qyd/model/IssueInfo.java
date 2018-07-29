package edu.tongji.sse.qyd.model;

import java.util.Date;
import java.util.Set;

/**
 * Created by qyd on 2018/5/9.
 */
public class IssueInfo {
    private Set<String> labels;
    private Date closedAt;
    private Date createdAt;
    private int number;
    private String url;

    public IssueInfo(int number) {
        this.number = number;
        this.closedAt = null;
        this.createdAt = null;
        this.labels = null;
        this.url = null;
    }

    public IssueInfo(int number, Date createdAt, Date closedAt, Set<String> labels, String url) {
        this.labels = labels;
        this.closedAt = closedAt;
        this.createdAt = createdAt;
        this.number = number;
        this.url = url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
