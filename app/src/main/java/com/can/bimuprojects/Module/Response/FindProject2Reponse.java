package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/11.
 * 找项目返回参数类2
 */

public class FindProject2Reponse {

    /**
     * exe_success : 1
     * total : 5
     * data : [{"brand_id":"2079","brand_name":"薄荷猫冻酸奶","brand_location":"北京","classify_id":"69","industry_id":"67","invest_amount":"14.54","shop_area":"0 平-60 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/57c7840c0b19f.png","brand_background":"http://v30.bimuwang.com/upload//brandLogo/589414a5eaf23.jpg","follow":0},{"brand_id":"1346","brand_name":"茶桔便 hey juice","brand_location":"杭州","classify_id":"63","industry_id":"62","invest_amount":"18.3","shop_area":"10 平-30 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/5875d4ac53982.png","brand_background":"http://v30.bimuwang.com/upload//brandLogo/589cb2386981d.jpg","follow":0},{"brand_id":"1345","brand_name":"一点点茶饮","brand_location":"上海","classify_id":"63","industry_id":"62","invest_amount":"10.3","shop_area":"0 平-50 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/571b1a556912a.png","brand_background":"http://v30.bimuwang.com/upload//brandLogo/589209fd2dffe.jpg","follow":0},{"brand_id":"1317","brand_name":"royaltea皇茶","brand_location":"广州","classify_id":"63","industry_id":"62","invest_amount":"19.25","shop_area":"20 平-80 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/5719b08294c22.png","brand_background":"http://v30.bimuwang.com/upload//brandLogo/589209a1ceea7.jpg","follow":0},{"brand_id":"2088","brand_name":"川中庭冒菜档口店","brand_location":"北京","classify_id":"55","industry_id":"50","invest_amount":"1.7","shop_area":"0 平-30 平","brand_logo":"http://v30.bimuwang.com/upload//brandLogo/582d1b1f2df54.jpg","brand_background":"http://v30.bimuwang.com/upload//brandLogo/589abf9b982b4.jpg","follow":1}]
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
         * brand_id : 2079
         * brand_name : 薄荷猫冻酸奶
         * brand_location : 北京
         * classify_id : 69
         * industry_id : 67
         * invest_amount : 14.54
         * shop_area : 0 平-60 平
         * brand_logo : http://v30.bimuwang.com/upload//brandLogo/57c7840c0b19f.png
         * brand_background : http://v30.bimuwang.com/upload//brandLogo/589414a5eaf23.jpg
         * follow : 0
         */

        private String brand_id;
        private String brand_name;
        private String brand_location;
        private String classify_id;
        private String industry_id;
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

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }
    }
}
