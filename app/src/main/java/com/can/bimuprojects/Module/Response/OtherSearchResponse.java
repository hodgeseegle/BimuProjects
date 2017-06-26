package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.Other_search;

import java.util.List;

/**
 * Created by can on 2017/4/17.
 */
public class OtherSearchResponse {

    private int exe_success;

    private List<Other_search> other_search;

    public OtherSearchResponse() {
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public int getExe_success() {
        return this.exe_success;
    }

    public void setOther_search(List<Other_search> other_search) {
        this.other_search = other_search;
    }

    public List<Other_search> getOther_search() {
        return this.other_search;
    }

}
