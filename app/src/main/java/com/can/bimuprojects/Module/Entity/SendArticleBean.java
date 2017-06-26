package com.can.bimuprojects.Module.Entity;


/**
 * Created by can on 2017/4/12.
 */
public class SendArticleBean  {
    /**
     * html格式的正文
     */
    String content;
    String uid;
    String title;

    public SendArticleBean() {
    }

    public SendArticleBean(String content, String uid, String title) {
        this.content = content;
        this.uid = uid;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
