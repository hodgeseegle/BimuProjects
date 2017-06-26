package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 17/4/19.
 */
public class GetUserInfoResponse {
    private int exe_success;
    private String user_image_url;
    private String user_name;
    private String user_level;
    private int article_count;
    private int follow_count;
    private int fans_count;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 0代表有未读消息，1代表没有未读消息
     */
    private int not_read_message;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getUser_image_url() {
        return user_image_url;
    }

    public void setUser_image_url(String user_image_url) {
        this.user_image_url = user_image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public int getArticle_count() {
        return article_count;
    }

    public void setArticle_count(int article_count) {
        this.article_count = article_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getNot_read_message() {
        return not_read_message;
    }

    public void setNot_read_message(int not_read_message) {
        this.not_read_message = not_read_message;
    }
}
