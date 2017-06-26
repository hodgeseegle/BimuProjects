package com.can.bimuprojects.Module.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by can on 2017/6/21.
 * 找项目记录
 */

public class GetFindProjectRecode {

    /**
     * uid : 145
     * amount : 0-30
     * class : 17,26
     * area : 0-200
     * locate : 北京
     * cname : 笨蛋,笨蛋笨蛋
     */

    private String uid;
    private String amount;
    @JsonProperty(value="class")
    private String classX;
    private String area;
    private String locate;
    private String cname;

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

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
