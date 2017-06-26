package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/19.
 */
public class GetUserFollowRequest {
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public GetUserFollowRequest(String uid) {
        this.uid = uid;
    }
}
