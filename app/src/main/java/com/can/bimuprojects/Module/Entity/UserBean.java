package com.can.bimuprojects.Module.Entity;

/**
 * Created by can on 2017/4/12.
 */
public class UserBean {
    private String uid;
    private String img_url;
    private String nick_name;
    private String type;
    private String focus_date;

    public String getFocus_date() {
        return focus_date;
    }

    public void setFocus_date(String focus_date) {
        this.focus_date = focus_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public UserBean(String uid, String img_url, String nick_name) {
        this.uid = uid;
        this.img_url = img_url;
        this.nick_name = nick_name;
    }

    public UserBean() {
    }
}
