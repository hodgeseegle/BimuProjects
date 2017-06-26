package com.can.bimuprojects.Module.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by can on 2017/4/12.
 */
public class MessageBean {
    @JsonProperty("title")
    private String mtitle;
    private String text;
    private String timestamp;
    private int is_read;
    private String id;
    private String author_img_url;
    private String type;
    private String comment_id;
    private String notice_id;
    private String is_comment;

    public String getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getAuthor_img_url() {
        return author_img_url;
    }

    public void setAuthor_img_url(String author_img_url) {
        this.author_img_url = author_img_url;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
