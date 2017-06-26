package com.can.bimuprojects.Module.Response;

/**
 * Created by pak2c on 16/4/12.
 */
public class FindPasswordResponse {
    private int exe_success;
    private int code;   // 1 为找回成功 ， 2为号码未注册
    private String uid;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
