package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/15.
 * 开店方案返回参数类
 */

public class OpenShopResponse {

    /**
     * exe_success : 0
     * data : [{"brand_id":"2049","brand_name":"台资味外卖","classify_id":"61","industry_id":"50","invest_amount":"5.8","brand_location":"天津","shop_area":"0 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_57690ac3bbe94.png"},{"brand_id":"2052","brand_name":"抱抱堂爆米花","classify_id":"61","industry_id":"50","invest_amount":"0.1","brand_location":"北京","shop_area":"0 平-10 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_576a3b110c661.png"},{"brand_id":"1805","brand_name":"皇家鸡排","classify_id":"61","industry_id":"50","invest_amount":"14","brand_location":"北京","shop_area":"20 平-30 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_571c3f595fa96.jpg"}]
     */

    private int exe_success;
    private List<DataBean> data;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * brand_id : 2049
         * brand_name : 台资味外卖
         * classify_id : 61
         * industry_id : 50
         * invest_amount : 5.8
         * brand_location : 天津
         * shop_area : 0 平-100 平
         * brand_logo : http://v30.bimuwang.com/upload/brandLogo/thumb_57690ac3bbe94.png
         */

        private String brand_id;
        private String brand_name;
        private String classify_id;
        private String industry_id;
        private String invest_amount;
        private String brand_location;
        private String shop_area;
        private String brand_logo;

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

        public String getBrand_location() {
            return brand_location;
        }

        public void setBrand_location(String brand_location) {
            this.brand_location = brand_location;
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
    }
}
