package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/13.
 */

public class SpecialResponse {

    /**
     * exe_success : 1
     * list : [{"type":3,"img_url":"http://30.bimuwang.com/upload/adPicture/58ee0f7cb7cea.jpg","title":"全年无淡季，这个家喻户晓的生意，你不赶紧抢着做？","id":"2595"},{"type":3,"img_url":"http://30.bimuwang.com/upload/adPicture/58ede0a7d2d31.jpg","title":"茶饮开店旺季之最受欢迎的茶饮品牌排行榜","id":"2592"},{"type":3,"img_url":"http://30.bimuwang.com/upload/adPicture/58ec87dd414f2.jpg","title":"最全盲测\u201c喜茶\u201d\u201c一点点\u201d\u201c爷茶\u201d等网红奶茶，胜出的居然是 ...","id":"2588"},{"type":3,"img_url":"http://30.bimuwang.com/upload/adPicture/58ecae8ab2e10.gif","title":"健康餐饮兴起，这些轻食餐厅，购物中心哭着闹着求它们进场","id":"2591"},{"type":3,"img_url":"http://30.bimuwang.com/upload/adPicture/58e84c22cc33e.jpg","title":"只有15万要想征服上班族的胃，你需要开这样的便当店","id":"2582"}]
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
         * type : 3
         * img_url : http://30.bimuwang.com/upload/adPicture/58ee0f7cb7cea.jpg
         * title : 全年无淡季，这个家喻户晓的生意，你不赶紧抢着做？
         * id : 2595
         */

        private int type;
        private String img_url;
        private String title;
        private String id;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
