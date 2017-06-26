package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/5/10.
 */
public class NotReadMsg {

    public  String exe_success;
    public int not_read_count;

    public NotReadMsg() {
    }

    public String getExe_success() {
        return exe_success;
    }

    public void setExe_success(String exe_success) {
        this.exe_success = exe_success;
    }

    public int getNot_read_count() {
        return not_read_count;
    }

    public void setNot_read_count(int not_read_count) {
        this.not_read_count = not_read_count;
    }
}
