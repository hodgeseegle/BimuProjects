package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/4/5.
 */
public class LoginResponse {
    private String exe_success;
    private String code;
    private String uid;

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
