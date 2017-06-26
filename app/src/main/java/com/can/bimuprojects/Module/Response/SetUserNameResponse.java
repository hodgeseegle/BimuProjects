package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/4/18.
 * 设置用户姓氏
 */

public class SetUserNameResponse {

    /**
     * exe_success : 1
     * res : ok
     */

    private int exe_success;
    private String res;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
