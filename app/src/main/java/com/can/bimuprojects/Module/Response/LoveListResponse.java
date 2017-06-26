package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 心愿单列表
 */

public class LoveListResponse {

    /**
     * exe_success : 1
     * total : 8
     * wish_list : [{"user_id":"4077","brand_id":"1677","is_consult":"0","brand_name":"赛百味","invest_amount":"67","brand_location":"上海","shop_area":"60 平- 80平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b3e1f63f67.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891f50442872.jpg","newarticle":0},{"user_id":"4077","brand_id":"1334","is_consult":"0","brand_name":"厚呷台式奶茶","invest_amount":"4.5","brand_location":"上海","shop_area":"15 平- 50平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5719862b060bd.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589207bd02d5f.jpg","newarticle":0},{"user_id":"4077","brand_id":"1299","is_consult":"0","brand_name":"快乐柠檬","invest_amount":"39","brand_location":"上海","shop_area":"20 平- 30平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5719af0f2727f.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589216a61bc68.jpg","newarticle":0},{"user_id":"4077","brand_id":"2087","is_consult":"0","brand_name":"山城小面","invest_amount":"1.2","brand_location":"北京","shop_area":"30 平- 100平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582d108e476e2.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589c774c7a1e7.jpg","newarticle":0},{"user_id":"4077","brand_id":"1935","is_consult":"0","brand_name":"香港驰记面家","invest_amount":"48","brand_location":"广州","shop_area":"50 平- 200平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5893ff6b36397.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58a55f9beae22.jpg","newarticle":0},{"user_id":"4077","brand_id":"2088","is_consult":"0","brand_name":"川中庭冒菜档口店","invest_amount":"1.7","brand_location":"北京","shop_area":"0 平- 30平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582d1b1f2df54.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589abf9b982b4.jpg","newarticle":0},{"user_id":"4077","brand_id":"2085","is_consult":"0","brand_name":"功夫炖鸡面","invest_amount":"2.8","brand_location":"北京","shop_area":"100 平- 300平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582d06f53986b.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589c8d17d154d.jpg","newarticle":0},{"user_id":"4077","brand_id":"2020","is_consult":"0","brand_name":"杜氏酸辣粉档口店","invest_amount":"1.7","brand_location":"北京","shop_area":"0 平- 30平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582c26c45a5cb.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589c79ac9ee01.jpg","newarticle":0}]
     * true_name : 李1
     */

    private int exe_success;
    private int total;
    private String true_name;
    private List<WishListBean> wish_list;

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

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public List<WishListBean> getWish_list() {
        return wish_list;
    }

    public void setWish_list(List<WishListBean> wish_list) {
        this.wish_list = wish_list;
    }

    public static class WishListBean {
        /**
         * user_id : 4077
         * brand_id : 1677
         * is_consult : 0
         * brand_name : 赛百味
         * invest_amount : 67
         * brand_location : 上海
         * shop_area : 60 平- 80平
         * brand_logo : http://v30.bimuwang.com/upload/brandLogo/571b3e1f63f67.jpg
         * brand_background : http://v30.bimuwang.com/upload/brandLogo/5891f50442872.jpg
         * newarticle : 0
         */

        private String user_id;
        private String brand_id;
        private String is_consult;
        private String brand_name;
        private String invest_amount;
        private String brand_location;
        private String shop_area;
        private String brand_logo;
        private String brand_background;
        private int newarticle;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getIs_consult() {
            return is_consult;
        }

        public void setIs_consult(String is_consult) {
            this.is_consult = is_consult;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
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

        public String getBrand_background() {
            return brand_background;
        }

        public void setBrand_background(String brand_background) {
            this.brand_background = brand_background;
        }

        public int getNewarticle() {
            return newarticle;
        }

        public void setNewarticle(int newarticle) {
            this.newarticle = newarticle;
        }
    }
}
