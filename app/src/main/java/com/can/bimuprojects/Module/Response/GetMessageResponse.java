package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 17/4/28.
 */
public class GetMessageResponse {
    private int exe_success;
    private String title;
    private String text;
    private String timestamp;

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getExe_success() {
        return exe_success;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
