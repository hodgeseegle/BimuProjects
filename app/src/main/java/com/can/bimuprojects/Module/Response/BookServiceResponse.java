package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 预约服务
 */

public class BookServiceResponse {

    /**
     * exe_success : 1
     * brand : {"brand_name":"薄荷猫冻酸奶","invest_amount":"14.54","brand_location":"北京","shop_area":"0 平- 60 平"}
     * service : [{"id":"78","title":"预约前往总部考察","detail":"<div><img src=\"http://30.bimuwang.com/upload/brandLogo/58c3f68963b54.jpg\" alt=\"bimu\" width=\"100%\" /><br /><\/div>                                                                                                    ","tip":["比目客服免费预约考察时间","品牌专员接待"],"logo":"http://v30.bimuwang.com/upload/brandLogo/58c3f63dc1913.jpg","choose":"false"}]
     */

    private int exe_success;
    private BrandBean brand;
    private List<ServiceBean> service;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public BrandBean getBrand() {
        return brand;
    }

    public void setBrand(BrandBean brand) {
        this.brand = brand;
    }

    public List<ServiceBean> getService() {
        return service;
    }

    public void setService(List<ServiceBean> service) {
        this.service = service;
    }

    public static class BrandBean {
        /**
         * brand_name : 薄荷猫冻酸奶
         * invest_amount : 14.54
         * brand_location : 北京
         * shop_area : 0 平- 60 平
         */

        private String brand_name;
        private String invest_amount;
        private String brand_location;
        private String shop_area;

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
    }

    public static class ServiceBean {
        /**
         * id : 78
         * title : 预约前往总部考察
         * detail : <div><img src="http://30.bimuwang.com/upload/brandLogo/58c3f68963b54.jpg" alt="bimu" width="100%" /><br /></div>
         * tip : ["比目客服免费预约考察时间","品牌专员接待"]
         * logo : http://v30.bimuwang.com/upload/brandLogo/58c3f63dc1913.jpg
         * choose : false
         */

        private String id;
        private String title;
        private String detail;
        private String logo;
        private String choose;
        private List<String> tip;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getChoose() {
            return choose;
        }

        public void setChoose(String choose) {
            this.choose = choose;
        }

        public List<String> getTip() {
            return tip;
        }

        public void setTip(List<String> tip) {
            this.tip = tip;
        }
    }
}
