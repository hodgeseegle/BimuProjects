package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/24.
 * 查看全部攻略
 */

public class GetLookAllRaidersRequest {

    /**
     * uid : 153
     * bid : 2094
     * timestamp : 0
     */

    private String uid;
    private String bid;
    private String p;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
