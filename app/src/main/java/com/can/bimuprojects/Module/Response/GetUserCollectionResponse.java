package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/26.
 */

public class GetUserCollectionResponse {

    /**
     * exe_success : 1
     * list : [{"article_id":"2550","article_summary":"许留山品牌溢价不值得，三线城市品牌不重要，性价比重要","img":"http://30.bimuwang.com/upload/articlePictures/58d63dff4a3ec.jpg"}]
     */

    private int exe_success;
    private List<ListBean> list;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * article_id : 2550
         * article_summary : 许留山品牌溢价不值得，三线城市品牌不重要，性价比重要
         * img : http://30.bimuwang.com/upload/articlePictures/58d63dff4a3ec.jpg
         */

        private String article_id;
        private String article_summary;
        private String img;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getArticle_summary() {
            return article_summary;
        }

        public void setArticle_summary(String article_summary) {
            this.article_summary = article_summary;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
