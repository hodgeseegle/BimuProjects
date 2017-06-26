package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/5/23.
 * 项目总数
 */

public class FindProjectTotalResponse {

    /**
     * exe_success : 1
     * total : 79
     * data : 79
     */

    private int exe_success;
    private String total;
    private String data;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
