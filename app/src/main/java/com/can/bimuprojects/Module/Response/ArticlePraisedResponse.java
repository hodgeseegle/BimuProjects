package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/4/18.
 * 文章收藏
 */

public class ArticlePraisedResponse {

    /**
     * exe_success : 1
     * code : 15
     * is_followed : 0
     */

    private int exe_success;
    private String code;
    private int is_followed;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(int is_followed) {
        this.is_followed = is_followed;
    }
}
