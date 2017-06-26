package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 17/4/11.
 */
public class QueryUpdateResponse {
    private int exe_success;

    private String apk_version;

    private String update_text;

    private String apk_url;

    private String force_update;


    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getApk_version() {
        return apk_version;
    }

    public void setApk_version(String apk_version) {
        this.apk_version = apk_version;
    }

    public String getUpdate_text() {
        return update_text;
    }

    public void setUpdate_text(String update_text) {
        this.update_text = update_text;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getForce_update() {
        return force_update;
    }

    public void setForce_update(String force_update) {
        this.force_update = force_update;
    }
}
