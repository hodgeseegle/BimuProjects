package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 淘汰品牌原因
 */

public class TypeServiceResponse {

    /**
     * exe_success : 1
     * data : ["加盟政策与我的投资期望不符","与品牌沟通后，品牌亮点不足","品牌并没有联系我","品牌老是电话骚扰","我的投资喜好改变了"]
     */

    private int exe_success;
    private List<String> data;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
