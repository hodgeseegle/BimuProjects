package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/12.
 */
public class FindPasswordRequest {
    String phone;
    String new_password;
    String verify;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }



}
