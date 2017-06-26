package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/29.
 */
public class ArticleDetailRequest {

    public String id;
    public String uid;
    public String timestamp;

    public ArticleDetailRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
