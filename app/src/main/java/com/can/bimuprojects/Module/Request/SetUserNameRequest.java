package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/17.
 * 设置用户名请求参数类
 */

public class SetUserNameRequest {

    /**
     * user_name : 我
     * user_sex : 0
     * uid : 324
     */

    private String user_name;
    private String user_sex;
    private String uid;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
