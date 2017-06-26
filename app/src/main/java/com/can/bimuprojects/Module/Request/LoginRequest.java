package com.can.bimuprojects.Module.Request;

/**
 * 登录请求实体类
 * Created by can on 2017/4/5.
 */
public class LoginRequest {


    String phone;
    String pwd;
    String tmpuid;
    String tmpsn;

    public String getTmpuid() {
        return tmpuid;
    }

    public void setTmpuid(String tmpuid) {
        this.tmpuid = tmpuid;
    }

    public String getTmpsn() {
        return tmpsn;
    }

    public void setTmpsn(String tmpsn) {
        this.tmpsn = tmpsn;
    }

    public LoginRequest(String phone, String pwd, String tmpuid, String tmpsn) {
        this.phone = phone;
        this.pwd = pwd;
        this.tmpuid = tmpuid;
        this.tmpsn = tmpsn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
