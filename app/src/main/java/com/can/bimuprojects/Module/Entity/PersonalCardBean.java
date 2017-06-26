package com.can.bimuprojects.Module.Entity;

import java.util.List;

/**
 * Created by can on 2017/4/12.
 */
public class PersonalCardBean {
    private String timestamp;

    private String article_id;

    private String text;

    private List<String> article_image_urls ;

    private String like_count;

    private String comment_count;

    private String has_praised;

    private String brand_tag;

    private String article_tag;

    private String classify_tag;

    private String classify_tag_id;
    private String article_tag_id;
    private String brand_tag_id;

    private String read_num;

    public PersonalCardBean() {
    }

    public String getClassify_tag_id() {
        return classify_tag_id;
    }

    public void setClassify_tag_id(String classify_tag_id) {
        this.classify_tag_id = classify_tag_id;
    }

    public String getArticle_tag_id() {
        return article_tag_id;
    }

    public void setArticle_tag_id(String article_tag_id) {
        this.article_tag_id = article_tag_id;
    }

    public String getBrand_tag_id() {
        return brand_tag_id;
    }

    public void setBrand_tag_id(String brand_tag_id) {
        this.brand_tag_id = brand_tag_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getArticle_image_urls() {
        return article_image_urls;
    }

    public void setArticle_image_urls(List<String> article_image_urls) {
        this.article_image_urls = article_image_urls;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getHas_praised() {
        return has_praised;
    }

    public void setHas_praised(String has_praised) {
        this.has_praised = has_praised;
    }

    public String getBrand_tag() {
        return brand_tag;
    }

    public void setBrand_tag(String brand_tag) {
        this.brand_tag = brand_tag;
    }

    public String getArticle_tag() {
        return article_tag;
    }

    public void setArticle_tag(String article_tag) {
        this.article_tag = article_tag;
    }

    public String getClassify_tag() {
        return classify_tag;
    }

    public void setClassify_tag(String classify_tag) {
        this.classify_tag = classify_tag;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }
}
