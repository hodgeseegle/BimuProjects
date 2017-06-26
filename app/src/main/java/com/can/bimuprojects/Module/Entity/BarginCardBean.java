package com.can.bimuprojects.Module.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 约惠界面卡片对应的实体类
 * Created by can on 2017/4/12.
 */
public class BarginCardBean {
    private String title;
    private String img_url;
    private String sign_up_num;
    @JsonProperty("index")
    private String mIndex;
    @JsonProperty("id")
    private String cid;
    private String pub_time;

    private String out_of_time;

    public BarginCardBean() {
    }

    public BarginCardBean(String title, String img_url, String sign_up_num, String mIndex, String
            cid,String pub_time) {
        this.title = title;
        this.img_url = img_url;
        this.sign_up_num = sign_up_num;
        this.mIndex = mIndex;
        this.cid = cid;
        this.pub_time=pub_time;
    }

    public String getPub_time() {
        return pub_time;
    }

    public void setPub_time(String pub_time) {
        this.pub_time = pub_time;
    }

    public String getmIndex() {
        return mIndex;
    }

    public void setmIndex(String mIndex) {
        this.mIndex = mIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSign_up_num() {
        return sign_up_num;
    }

    public void setSign_up_num(String sign_up_num) {
        this.sign_up_num = sign_up_num;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


    public String getOut_of_time() {
        return out_of_time;
    }

    public void setOut_of_time(String out_of_time) {
        this.out_of_time = out_of_time;
    }
}
