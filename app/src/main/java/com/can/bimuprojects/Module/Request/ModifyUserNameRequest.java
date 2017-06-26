package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/21.
 */
public class ModifyUserNameRequest {
    private String uid;
    private String new_name;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNew_name() {
        return new_name;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }
}
