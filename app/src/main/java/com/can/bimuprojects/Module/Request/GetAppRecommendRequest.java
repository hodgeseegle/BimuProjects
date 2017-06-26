package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/5/16.
 * 应用推荐
 */

public class GetAppRecommendRequest {

    /**
     * uid : 4189
     * type : 1
     * p :
     */

    private String uid;
    private String type;
    private String p;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
