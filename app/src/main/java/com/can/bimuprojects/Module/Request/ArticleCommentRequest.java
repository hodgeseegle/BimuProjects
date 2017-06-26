package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/18.\
 * 文章评论
 */

public class ArticleCommentRequest {

    /**
     * article_id : 2493
     * uid : 4189
     * is_replay : 0
     * re_comment_id : 0
     * text : 哈哈哈
     */

    private String article_id;
    private String uid;
    private int is_replay;
    private int re_comment_id;
    private String text;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIs_replay() {
        return is_replay;
    }

    public void setIs_replay(int is_replay) {
        this.is_replay = is_replay;
    }

    public int getRe_comment_id() {
        return re_comment_id;
    }

    public void setRe_comment_id(int re_comment_id) {
        this.re_comment_id = re_comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
