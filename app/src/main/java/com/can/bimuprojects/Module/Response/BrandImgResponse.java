package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/17.
 * 图库
 */

public class BrandImgResponse {


    /**
     * exe_success : 0
     * total : 0
     * data : []
     */

    private int exe_success;
    private int total;
    private List<String> data;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
