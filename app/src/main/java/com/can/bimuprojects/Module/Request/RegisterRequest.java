package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/12.
 */
public class RegisterRequest {
    public String phone;
    public String password;
    public String verify;
    public String user_name;
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

    public RegisterRequest() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
