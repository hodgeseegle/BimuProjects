package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.BarginCardBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/12.
 */
public class BarginResponse {
    private int exe_success;
    private List<BarginCardBean> bargin_list;

    public List<BarginCardBean> getBargin_list() {
        return bargin_list;
    }

    public void setBargin_list(ArrayList<BarginCardBean> bargin_list) {
        this.bargin_list = bargin_list;
    }

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public void setBargin_list(List<BarginCardBean> bargin_list) {
        this.bargin_list = bargin_list;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
