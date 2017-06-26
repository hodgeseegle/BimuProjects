package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/12.
 */
public class UploadPicRequest {

    private String pic;
    private int type;
    private String uid;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public UploadPicRequest(String pic, int type, String uid) {
        this.pic = pic;
        this.type = type;
        this.uid = uid;
    }
}
