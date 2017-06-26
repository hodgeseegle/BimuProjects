package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/4/12.
 */
public class GetVerifyCodeResponse {
    private int exe_success;
    private int has_send;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public int getHas_send() {
        return has_send;
    }

    public void setHas_send(int has_send) {
        this.has_send = has_send;
    }
}
