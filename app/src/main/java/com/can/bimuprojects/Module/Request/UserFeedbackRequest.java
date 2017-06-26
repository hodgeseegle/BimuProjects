package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/12.
 */
public class UserFeedbackRequest {
    private String uid;
    private String content;

    public UserFeedbackRequest(String uid, String content) {
        this.uid = uid;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
