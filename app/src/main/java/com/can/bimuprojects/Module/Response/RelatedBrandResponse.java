package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/18.
 * 相关品牌
 */

public class RelatedBrandResponse {

    /**
     * exe_success : 1
     * brands : [{"brand_id":"2063","brand_name":"摇滚三国麻辣e站","brand_location":"北京","industry_id":"50","classify_id":"56","invest_amount":"33.2","shop_area":"60 平-120 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5779df63d5a39.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891aefa9fb88.jpg","follow":0}]
     * cards : []
     */

    private int exe_success;
    private List<BrandsBean> brands;
    private List<?> cards;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<BrandsBean> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandsBean> brands) {
        this.brands = brands;
    }

    public List<?> getCards() {
        return cards;
    }

    public void setCards(List<?> cards) {
        this.cards = cards;
    }

    public static class BrandsBean {
        /**
         * brand_id : 2063
         * brand_name : 摇滚三国麻辣e站
         * brand_location : 北京
         * industry_id : 50
         * classify_id : 56
         * invest_amount : 33.2
         * shop_area : 60 平-120 平
         * brand_logo : http://v30.bimuwang.com/upload/brandLogo/5779df63d5a39.png
         * brand_background : http://v30.bimuwang.com/upload/brandLogo/5891aefa9fb88.jpg
         * follow : 0
         */

        private String brand_id;
        private String brand_name;
        private String brand_location;
        private String industry_id;
        private String classify_id;
        private String invest_amount;
        private String shop_area;
        private String brand_logo;
        private String brand_background;
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

        public String getIndustry_id() {
            return industry_id;
        }

        public void setIndustry_id(String industry_id) {
            this.industry_id = industry_id;
        }

        public String getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(String classify_id) {
            this.classify_id = classify_id;
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

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }
    }
}
