package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 17/4/15.
 */
public class UploadPicResponse {
    private int exe_success;
    private String url;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}