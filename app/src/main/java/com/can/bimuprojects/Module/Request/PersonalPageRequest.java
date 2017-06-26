package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/23.
 */
public class PersonalPageRequest {

    public String uid;
    public String access_uid;
    public String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public PersonalPageRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccess_uid() {
        return access_uid;
    }

    public void setAccess_uid(String access_uid) {
        this.access_uid = access_uid;
    }
}
