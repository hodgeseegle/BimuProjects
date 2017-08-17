package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/08/17.
 */

public class ProjectResponse {

    /**
     * exe_success : 1
     * data : [{"brand_id":"2063","brand_name":"摇滚三国麻辣e站","brand_location":"北京","home_look_time":"373","classify_id":"56","industry_id":"50","invest_amount":"33.2","shop_area":"60 平-120 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/5779df63d5a39.png","brand_background":"http://v30.bimuwang.com/upload//brandLogo/5891aefa9fb88.jpg","num":"2","follow":1},{"brand_id":"2094","brand_name":"wowwoo熊港式小食","brand_location":"广州","home_look_time":"2104","classify_id":"68","industry_id":"18","invest_amount":"19","shop_area":"15 平以上","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/585a31e9cb254.png","brand_background":"http://v30.bimuwang.com/upload//brandLogo/58953ad5de4ae.jpg","num":"1","follow":1},{"brand_id":"2090","brand_name":"比比味鸡排","brand_location":"广州","home_look_time":"841","classify_id":"61","industry_id":"50","invest_amount":"13.35","shop_area":"0 平-25 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/583694d2d1cec.jpg","brand_background":"http://v30.bimuwang.com/upload//brandLogo/5891a234ea71a.jpg","num":"1","follow":1},{"brand_id":"2088","brand_name":"川中庭冒菜档口店","brand_location":"北京","home_look_time":"728","classify_id":"55","industry_id":"50","invest_amount":"1.7","shop_area":"0 平-30 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/582d1b1f2df54.jpg","brand_background":"http://v30.bimuwang.com/upload//brandLogo/589abf9b982b4.jpg","num":"1","follow":0},{"brand_id":"1816","brand_name":"大邻洞首尔炸鸡创业店","brand_location":"上海","home_look_time":"3363","classify_id":"52","industry_id":"50","invest_amount":"11.98","shop_area":"20 平-30 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/5895db670c79d.jpg","brand_background":"http://v30.bimuwang.com/upload//brandLogo/5895db6bddb9f.jpg","num":"1","follow":1}]
     * total : 5
     */

    private int exe_success;
    private int total;
    private List<DataBean> data;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * brand_id : 2063
         * brand_name : 摇滚三国麻辣e站
         * brand_location : 北京
         * home_look_time : 373
         * classify_id : 56
         * industry_id : 50
         * invest_amount : 33.2
         * shop_area : 60 平-120 平
         * brand_logo : http://v30.bimuwang.com/upload//brandLogo/5779df63d5a39.png
         * brand_background : http://v30.bimuwang.com/upload//brandLogo/5891aefa9fb88.jpg
         * num : 2
         * follow : 1
         */

        private String brand_id;
        private String brand_name;
        private String brand_location;
        private String home_look_time;
        private String classify_id;
        private String industry_id;
        private String invest_amount;
        private String shop_area;
        private String brand_logo;
        private String brand_background;
        private String num;
        private int follow;

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_location() {
            return brand_location;
        }

        public void setBrand_location(String brand_location) {
            this.brand_location = brand_location;
        }

        public String getHome_look_time() {
            return home_look_time;
        }

        public void setHome_look_time(String home_look_time) {
            this.home_look_time = home_look_time;
        }

        public String getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(String classify_id) {
            this.classify_id = classify_id;
        }

        public String getIndustry_id() {
            return industry_id;
        }

        public void setIndustry_id(String industry_id) {
            this.industry_id = industry_id;
        }

        public String getInvest_amount() {
            return invest_amount;
        }

        public void setInvest_amount(String invest_amount) {
            this.invest_amount = invest_amount;
        }

        public String getShop_area() {
            return shop_area;
        }

        public void setShop_area(String shop_area) {
            this.shop_area = shop_area;
        }

        public String getBrand_logo() {
            return brand_logo;
        }

        public void setBrand_logo(String brand_logo) {
            this.brand_logo = brand_logo;
        }

        public String getBrand_background() {
            return brand_background;
        }

        public void setBrand_background(String brand_background) {
            this.brand_background = brand_background;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }
    }
}
