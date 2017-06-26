package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/28.
 */
public class GetMessageListRequest {
    private String uid;
    private String timestamp;

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
