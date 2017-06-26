package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/24.
 * 专题文章列表
 */

public class GetSpecialArticleRequest {

    /**
     * type : 0
     * uid : 6106
     * ad_id : 316
     * id_type : 2
     */

    private String type;
    private String uid;
    private String ad_id;
    private String id_type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }
}
