package com.can.bimuprojects.Module.Entity;

import java.util.List;

/**
 * Created by wiipu on 2016/3/16.
 */
public class RecommandCardBean {

    /**
     * timestamp : 1489045458
     * article_id : 2471
     * author_id : 1894
     * author_image : http://v30.bimuwang.com/upload/userLogo/1481876182u1894.jpg
     * author_nickname : 谷今
     * text : “摇滚三国”真实探店 | 这家麻辣烫，竟让我吃出了火锅的感觉
     * brand_tag : null
     * brand_tag_id : 0
     * article_tag : 考察日记
     * article_tag_id : 2
     * classify_tag : null
     * classify_tag_id : 0
     * article_image_urls : ["http://v30.bimuwang.com/upload/articlePictures/thumb_58c1076a73fe9.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1078b14b40.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1079fc9399.jpg"]
     * like_count : 1
     * has_followed : 0
     * has_praised : 0
     * sort_weight : 0
     * comment_count : 1
     * read_num : 1173
     * article_summary : “摇滚三国”真实探店 | 这家麻辣烫，竟让我吃出了火锅的感觉
     * rticle_html : 都说麻辣烫是一个人的火锅，而火锅是一群人的麻辣烫，但说到底麻辣烫跟火锅还是有区别的，比起火锅还要一步步的涮，直接一大碗煮好端上的麻辣烫可就简单多了。 应该说火锅跟麻辣烫各有各的好吧，但这家“摇滚三国麻辣烫”却让我吃出了火锅的感觉，因为它在很多细节方面更像是火锅而不是麻辣烫。 先说一下装修，摇滚三国麻
     */

    private String timestamp;
    private String article_id;
    private String author_id;
    private String author_image;
    private String author_nickname;
    private String text;
    private Object brand_tag;
    private String brand_tag_id;
    private String article_tag;
    private String article_tag_id;
    private Object classify_tag;
    private String classify_tag_id;
    private String like_count;
    private int has_followed;
    private int has_praised;
    private String sort_weight;
    private String comment_count;
    private int read_num;
    private String article_summary;
    private String rticle_html;
    private List<String> article_image_urls;

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

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getBrand_tag() {
        return brand_tag;
    }

    public void setBrand_tag(Object brand_tag) {
        this.brand_tag = brand_tag;
    }

    public String getBrand_tag_id() {
        return brand_tag_id;
    }

    public void setBrand_tag_id(String brand_tag_id) {
        this.brand_tag_id = brand_tag_id;
    }

    public String getArticle_tag() {
        return article_tag;
    }

    public void setArticle_tag(String article_tag) {
        this.article_tag = article_tag;
    }

    public String getArticle_tag_id() {
        return article_tag_id;
    }

    public void setArticle_tag_id(String article_tag_id) {
        this.article_tag_id = article_tag_id;
    }

    public Object getClassify_tag() {
        return classify_tag;
    }

    public void setClassify_tag(Object classify_tag) {
        this.classify_tag = classify_tag;
    }

    public String getClassify_tag_id() {
        return classify_tag_id;
    }

    public void setClassify_tag_id(String classify_tag_id) {
        this.classify_tag_id = classify_tag_id;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public int getHas_followed() {
        return has_followed;
    }

    public void setHas_followed(int has_followed) {
        this.has_followed = has_followed;
    }

    public int getHas_praised() {
        return has_praised;
    }

    public void setHas_praised(int has_praised) {
        this.has_praised = has_praised;
    }

    public String getSort_weight() {
        return sort_weight;
    }

    public void setSort_weight(String sort_weight) {
        this.sort_weight = sort_weight;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public String getArticle_summary() {
        return article_summary;
    }

    public void setArticle_summary(String article_summary) {
        this.article_summary = article_summary;
    }

    public String getRticle_html() {
        return rticle_html;
    }

    public void setRticle_html(String rticle_html) {
        this.rticle_html = rticle_html;
    }

    public List<String> getArticle_image_urls() {
        return article_image_urls;
    }

    public void setArticle_image_urls(List<String> article_image_urls) {
        this.article_image_urls = article_image_urls;
    }
}
