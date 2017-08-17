package com.can.bimuprojects.Module.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by can on 2017/08/17.
 * 项目库
 */

public class ProjectRequest {


    /**
     * uid : 6589
     * page : 0
     * area :
     * amount : 100-1000
     * class :
     * sort :
     */

    private String uid;
    private String page;
    private String area;
    private String amount;
    @JsonProperty("class")
    private String classX;
    private String sort;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
