package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/15.
 */
public class FocusRequest {

    private int type;
    private String uid;
    private String id;
    private String client_version;
    private String client_type;

    public String getClient_version() {
        return client_version;
    }

    public void setClient_version(String client_version) {
        this.client_version = client_version;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

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
