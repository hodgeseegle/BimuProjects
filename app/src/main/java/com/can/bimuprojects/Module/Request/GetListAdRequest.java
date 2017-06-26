package com.can.bimuprojects.Module.Request;

/**
 * Created by pak2c on 16/4/28.
 * Email : 9532244366@qq.com
 */
public class GetListAdRequest {
    private String uid;
    private String ad_id;
    private int type=0;
    private String timestamp;
    private int id_type;

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }
}
