package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/6/21.
 */

public class SNResponse {

    /**
     * exe_success : 1
     * code : 0
     * uid : 6175
     * sn : 1213kjkjkajSDJKASJDKAJDS
     */

    private int exe_success;
    private int code;
    private String uid;
    private String sn;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
