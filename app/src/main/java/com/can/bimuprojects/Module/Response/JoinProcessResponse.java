package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/4/14.
 */

public class JoinProcessResponse {

    /**
     * exe_success : 1
     * data : <img src="http://30.bimuwang.com/upload/noticePictures/58c20c4e1c324.jpg" alt="bimu" width="100%" />
     */

    private int exe_success;
    private String data;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
