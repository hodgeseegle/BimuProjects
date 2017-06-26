package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/27.
 * * 用户活动（约惠）
 */

public class GetUserDiscountRequest {

    /**
     * uid : 6100
     * index : 0
     */

    private String uid;
    private String index;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
