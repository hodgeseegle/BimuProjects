package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 2017/4/9.
 */

public class SearchResultRequest {

    /**
     * uid : 4077
     * type : 0
     * keyword : 奶
     * sort :
     * page : 1
     */

    private String uid;
    private String type;
    private String keyword;
    private String sort;
    private String page;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
