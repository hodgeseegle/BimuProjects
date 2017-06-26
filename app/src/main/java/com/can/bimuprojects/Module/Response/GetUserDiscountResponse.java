package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/27.
 * 用户活动（约惠）
 */

public class GetUserDiscountResponse {

    /**
     * exe_success : 1
     * bargin_list : [{"title":"wowwoo活动推广","img_url":"http://v30.bimuwang.com/upload/activityPictures/58f71ef4355ea.jpg","sign_up_num":"10","id":"156","index":0,"pub_time":"1492590425"},{"title":"wowo上线测试","img_url":"http://v30.bimuwang.com/upload/activityPictures/58fb1c0c1f2e4.jpg","sign_up_num":"17","id":"160","index":1,"pub_time":"1492852098"}]
     */

    private int exe_success;
    private List<BarginListBean> bargin_list;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<BarginListBean> getBargin_list() {
        return bargin_list;
    }

    public void setBargin_list(List<BarginListBean> bargin_list) {
        this.bargin_list = bargin_list;
    }

    public static class BarginListBean {
        /**
         * title : wowwoo活动推广
         * img_url : http://v30.bimuwang.com/upload/activityPictures/58f71ef4355ea.jpg
         * sign_up_num : 10
         * id : 156
         * index : 0
         * pub_time : 1492590425
         */

        private String title;
        private String img_url;
        private String sign_up_num;
        private String id;
        private int index;
        private String pub_time;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }
    }
}
