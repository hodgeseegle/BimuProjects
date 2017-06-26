package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/4/12.
 */
public class RegisterResponse {
    public String exe_success;
    public String code;
    public String uid;

    public RegisterResponse() {
    }

    public String getExe_success() {
        return exe_success;
    }

    public void setExe_success(String exe_success) {
        this.exe_success = exe_success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
