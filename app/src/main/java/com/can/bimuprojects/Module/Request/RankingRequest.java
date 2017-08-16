package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/08/16.
 * 实时热门排行
 */

public class RankingRequest {

    /**
     * uid :
     * cid :
     * page :
     * area :
     * type :
     */

    private String uid;
    private String cid;
    private String page;
    private String area;
    private String type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
