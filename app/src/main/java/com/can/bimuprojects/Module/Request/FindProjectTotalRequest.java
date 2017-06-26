package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/5/23.
 * 项目总数
 */

public class FindProjectTotalRequest {

    /**
     * uid : 4077
     * amount : 100
     * cid :
     * area :
     * page : 0
     * type : 0
     */

    private String uid;
    private String amount;
    private String cid;
    private String area;
    private String page;
    private String type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
