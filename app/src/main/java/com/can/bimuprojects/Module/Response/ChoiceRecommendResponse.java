package com.can.bimuprojects.Module.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by can on 2017/08/16.
 */

public class ChoiceRecommendResponse implements Serializable {

    /**
     * exe_success : 1
     * data : ok
     * brand_label : [{"name":"文能是笨蛋","label_img":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","data":[{"brand_id":"2090","brand_name":"比比味鸡排","classify_id":"61","invest_amount":"13.35","brand_location":"广州","shop_area":"0 平-25 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891a234ea71a.jpg","autof":"认证","autos":"官方","scribe":90},{"brand_id":"2093","brand_name":"罐子里的约会","classify_id":"45","invest_amount":"22","brand_location":"宁波","shop_area":"0 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5854d417ba63b.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589413d4c48bd.jpg","autof":"认证","autos":"官方","scribe":93},{"brand_id":"2092","brand_name":"吉满杯","classify_id":"27","invest_amount":"20.3","brand_location":"苏州","shop_area":"15 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5852003b61960.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5895bcc5cbf2d.jpg","autof":"","autos":"","scribe":92},{"brand_id":"2088","brand_name":"川中庭冒菜档口店","classify_id":"55","invest_amount":"1.7","brand_location":"北京","shop_area":"0 平-30 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582d1b1f2df54.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589abf9b982b4.jpg","autof":"","autos":"","scribe":88},{"brand_id":"2087","brand_name":"山城小面","classify_id":"20","invest_amount":"1.2","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582d108e476e2.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589c774c7a1e7.jpg","autof":"","autos":"","scribe":87}]},{"name":"冠锋是","label_img":"http://v30.bimuwang.com/upload/brandLogo/5891ada0943a8.jpg","data":[{"brand_id":"2153","brand_name":"川中庭冒菜特色店","classify_id":"55","invest_amount":"2.5","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5891ada0943a8.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891ad79e8441.jpg","autof":"","autos":"","scribe":153}]},{"name":"温柔","label_img":"http://v30.bimuwang.com/upload/brandLogo/5891ea4901c34.jpg","data":[{"brand_id":"2141","brand_name":"金花妹米粉特色店","classify_id":"58","invest_amount":"2.5","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5891ea4901c34.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891ea427ef85.jpg","autof":"","autos":"","scribe":141},{"brand_id":"2139","brand_name":"杜氏酸辣粉特色店","classify_id":"58","invest_amount":"2.5","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5881d0510bc77.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5881d055e788a.jpg","autof":"","autos":"","scribe":139}]},{"name":"1234","label_img":"http://v30.bimuwang.com/upload/brandLogo/589812be132ba.jpg","data":[{"brand_id":"2160","brand_name":"杜中记和气粥铺","classify_id":"61","invest_amount":"2.8","brand_location":"北京","shop_area":"100 平-300 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/589812be132ba.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5881ce2e8c290.jpg","autof":"","autos":"","scribe":160},{"brand_id":"2159","brand_name":"杜中记烧肉拌饭","classify_id":"61","invest_amount":"2.8","brand_location":"北京","shop_area":"100 平-300 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58981280a3a18.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891a53fbefa5.jpg","autof":"","autos":"","scribe":159},{"brand_id":"2153","brand_name":"川中庭冒菜特色店","classify_id":"55","invest_amount":"2.5","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5891ada0943a8.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891ad79e8441.jpg","autof":"","autos":"","scribe":153},{"brand_id":"2141","brand_name":"金花妹米粉特色店","classify_id":"58","invest_amount":"2.5","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5891ea4901c34.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891ea427ef85.jpg","autof":"","autos":"","scribe":141}]},{"name":"俄武器恶气","label_img":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","data":[{"brand_id":"2090","brand_name":"比比味鸡排","classify_id":"61","invest_amount":"13.35","brand_location":"广州","shop_area":"0 平-25 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891a234ea71a.jpg","autof":"认证","autos":"官方","scribe":90},{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2481","brand_name":"打放松的","classify_id":"42","invest_amount":"23","brand_location":"123","shop_area":"1 平-234 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5983c800d353a.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5983c81d21d72.jpg","autof":"","autos":"","scribe":481}]},{"name":"请问请问","label_img":"http://v30.bimuwang.com/upload/brandLogo/587f1e70d4d58.jpg","data":[{"brand_id":"2356","brand_name":"REMICONE乌云冰淇淋大型店","classify_id":"46","invest_amount":"16.8","brand_location":"上海","shop_area":"65 平-70 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/587f1e70d4d58.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f1e759ee68.jpg","autof":"","autos":"","scribe":356},{"brand_id":"2344","brand_name":"可爱可亲母婴豪华店","classify_id":"71","invest_amount":"21.98","brand_location":"广州","shop_area":"150 平-300 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/587f21e08b56a.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f22b69acfa.jpg","autof":"","autos":"","scribe":344},{"brand_id":"2343","brand_name":"可爱可亲母婴高级店","classify_id":"71","invest_amount":"10.98","brand_location":"广州","shop_area":"70 平-150 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/587f22e01f7a7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f22e47e7a1.jpg","autof":"","autos":"","scribe":343},{"brand_id":"356","brand_name":"新来福化妆品","classify_id":"73","invest_amount":"10.6","brand_location":"沈阳","shop_area":"65 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57188b274f0ed.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5892a159b5e3b.jpg","autof":"","autos":"","scribe":356},{"brand_id":"1892","brand_name":"杏记甜品","classify_id":"68","invest_amount":"19.48","brand_location":"广州","shop_area":"30 平-150 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5892dd1e3a62c.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5892dd252b092.jpg","autof":"","autos":"","scribe":892}]},{"name":"为全文","label_img":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","data":[{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2481","brand_name":"打放松的","classify_id":"42","invest_amount":"23","brand_location":"123","shop_area":"1 平-234 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5983c800d353a.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5983c81d21d72.jpg","autof":"","autos":"","scribe":481},{"brand_id":"2480","brand_name":"滴滴打车","classify_id":"88","invest_amount":"123","brand_location":"13","shop_area":"21 平-23 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/59702a060708f.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/59702a0c5a377.jpg","autof":"认证","autos":"比目大神","scribe":480},{"brand_id":"2094","brand_name":"wowwoo熊港式小食","classify_id":"68","invest_amount":"19","brand_location":"广州","shop_area":"15 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/585a31e9cb254.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58953ad5de4ae.jpg","autof":"有吃有喝","autos":"无淡旺季","scribe":94},{"brand_id":"2153","brand_name":"川中庭冒菜特色店","classify_id":"55","invest_amount":"2.5","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5891ada0943a8.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891ad79e8441.jpg","autof":"","autos":"","scribe":153}]},{"name":"我是文能我是猪","label_img":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","data":[{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2481","brand_name":"打放松的","classify_id":"42","invest_amount":"23","brand_location":"123","shop_area":"1 平-234 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5983c800d353a.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5983c81d21d72.jpg","autof":"","autos":"","scribe":481},{"brand_id":"2480","brand_name":"滴滴打车","classify_id":"88","invest_amount":"123","brand_location":"13","shop_area":"21 平-23 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/59702a060708f.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/59702a0c5a377.jpg","autof":"认证","autos":"比目大神","scribe":480},{"brand_id":"2094","brand_name":"wowwoo熊港式小食","classify_id":"68","invest_amount":"19","brand_location":"广州","shop_area":"15 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/585a31e9cb254.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58953ad5de4ae.jpg","autof":"有吃有喝","autos":"无淡旺季","scribe":94},{"brand_id":"2153","brand_name":"川中庭冒菜特色店","classify_id":"55","invest_amount":"2.5","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5891ada0943a8.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891ad79e8441.jpg","autof":"","autos":"","scribe":153}]},{"name":"绕弯儿味儿问问人","label_img":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","data":[{"brand_id":"2090","brand_name":"比比味鸡排","classify_id":"61","invest_amount":"13.35","brand_location":"广州","shop_area":"0 平-25 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891a234ea71a.jpg","autof":"认证","autos":"官方","scribe":90},{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2481","brand_name":"打放松的","classify_id":"42","invest_amount":"23","brand_location":"123","shop_area":"1 平-234 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5983c800d353a.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5983c81d21d72.jpg","autof":"","autos":"","scribe":481},{"brand_id":"2480","brand_name":"滴滴打车","classify_id":"88","invest_amount":"123","brand_location":"13","shop_area":"21 平-23 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/59702a060708f.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/59702a0c5a377.jpg","autof":"认证","autos":"比目大神","scribe":480},{"brand_id":"2094","brand_name":"wowwoo熊港式小食","classify_id":"68","invest_amount":"19","brand_location":"广州","shop_area":"15 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/585a31e9cb254.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58953ad5de4ae.jpg","autof":"有吃有喝","autos":"无淡旺季","scribe":94}]}]
     */

    private int exe_success;
    private String data;
    private List<BrandLabelBean> brand_label;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<BrandLabelBean> getBrand_label() {
        return brand_label;
    }

    public void setBrand_label(List<BrandLabelBean> brand_label) {
        this.brand_label = brand_label;
    }

    public static class BrandLabelBean implements Serializable{
        /**
         * name : 文能是笨蛋
         * label_img : http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg
         * data : [{"brand_id":"2090","brand_name":"比比味鸡排","classify_id":"61","invest_amount":"13.35","brand_location":"广州","shop_area":"0 平-25 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5891a234ea71a.jpg","autof":"认证","autos":"官方","scribe":90},{"brand_id":"2093","brand_name":"罐子里的约会","classify_id":"45","invest_amount":"22","brand_location":"宁波","shop_area":"0 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5854d417ba63b.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589413d4c48bd.jpg","autof":"认证","autos":"官方","scribe":93},{"brand_id":"2092","brand_name":"吉满杯","classify_id":"27","invest_amount":"20.3","brand_location":"苏州","shop_area":"15 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5852003b61960.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5895bcc5cbf2d.jpg","autof":"","autos":"","scribe":92},{"brand_id":"2088","brand_name":"川中庭冒菜档口店","classify_id":"55","invest_amount":"1.7","brand_location":"北京","shop_area":"0 平-30 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582d1b1f2df54.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589abf9b982b4.jpg","autof":"","autos":"","scribe":88},{"brand_id":"2087","brand_name":"山城小面","classify_id":"20","invest_amount":"1.2","brand_location":"北京","shop_area":"30 平-100 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/582d108e476e2.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589c774c7a1e7.jpg","autof":"","autos":"","scribe":87}]
         */

        private String name;
        private String label_img;
        private List<DataBean> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabel_img() {
            return label_img;
        }

        public void setLabel_img(String label_img) {
            this.label_img = label_img;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            /**
             * brand_id : 2090
             * brand_name : 比比味鸡排
             * classify_id : 61
             * invest_amount : 13.35
             * brand_location : 广州
             * shop_area : 0 平-25 平
             * brand_logo : http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg
             * brand_background : http://v30.bimuwang.com/upload/brandLogo/5891a234ea71a.jpg
             * autof : 认证
             * autos : 官方
             * scribe : 90
             */

            private String brand_id;
            private String brand_name;
            private String classify_id;
            private String invest_amount;
            private String brand_location;
            private String shop_area;
            private String brand_logo;
            private String brand_background;
            private String autof;
            private String autos;
            private String scribe;

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

            public String getAutof() {
                return autof;
            }

            public void setAutof(String autof) {
                this.autof = autof;
            }

            public String getAutos() {
                return autos;
            }

            public void setAutos(String autos) {
                this.autos = autos;
            }

            public String getScribe() {
                return scribe;
            }

            public void setScribe(String scribe) {
                this.scribe = scribe;
            }
        }
    }
}
