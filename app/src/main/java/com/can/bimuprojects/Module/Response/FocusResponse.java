package com.can.bimuprojects.Module.Response;

/**
 * Created by pak2c on 16/4/15.
 */
public class FocusResponse {
    private int exe_success;
    private int code;
    private int is_followed;

    public FocusResponse() {
    }

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

    public int getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(int is_followed) {
        this.is_followed = is_followed;
    }
}
