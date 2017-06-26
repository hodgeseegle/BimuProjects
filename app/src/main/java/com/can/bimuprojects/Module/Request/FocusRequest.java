package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/15.
 */
public class FocusRequest {

    private int type;
    private String uid;
    private String id;

    public FocusRequest() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FocusRequest(int type, String uid, String id) {
        this.type = type;
        this.uid = uid;
        this.id = id;
    }
}
