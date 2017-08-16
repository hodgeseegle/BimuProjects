package com.can.bimuprojects.Module.Response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by can on 2017/08/16.
 * 实时热门排行
 */

public class RankingResponse implements Serializable{

    /**
     * exe_success : 1
     * brand_industry : [{"name":"新锐","data":[{"brand_id":"2060","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5773ef5b08227.png","brand_name":"友宝自动贩卖机","invest_amount":"2.1","shop_area":"0 平-5平","shop_max_area":"5","brand_location":"深圳","tag":["哈皮","二哈"]},{"brand_id":"2075","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57a82e21a27f7.png","brand_name":"海马体照相馆","invest_amount":"0","shop_area":"0 平-500平","shop_max_area":"500","brand_location":"杭州","tag":["",""]},{"brand_id":"338","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5763c0d591bad.png","brand_name":"e袋洗互联网洗衣","invest_amount":"80","shop_area":"200 平以上","shop_max_area":"0","brand_location":"北京","tag":["",""]},{"brand_id":"352","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5714e05f0354b.png","brand_name":"QC快发快剪","invest_amount":"5.88","shop_area":"12 平-15平","shop_max_area":"15","brand_location":"杭州","tag":["",""]},{"brand_id":"2038","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/574fa09f1e081.png","brand_name":"网鱼网咖","invest_amount":"150","shop_area":"0 平-360平","shop_max_area":"360","brand_location":"上海","tag":["",""]}]},{"name":"餐","data":[{"brand_id":"2090","industry_id":"50","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","brand_name":"比比味鸡排","invest_amount":"13.35","shop_area":"0 平-25平","shop_max_area":"25","brand_location":"广州","tag":["认证","官方"]},{"brand_id":"2482","industry_id":"50","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/598adc7d36f82.jpg","brand_name":"测试","invest_amount":"70","shop_area":"23 平-52平","shop_max_area":"52","brand_location":"广州","tag":["官方","比目认证品牌"]},{"brand_id":"2479","industry_id":"50","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_name":"王子品牌","invest_amount":"232","shop_area":"123 平-123平","shop_max_area":"123","brand_location":"北京","tag":["",""]},{"brand_id":"2481","industry_id":"50","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5983c800d353a.jpg","brand_name":"打放松的","invest_amount":"23","shop_area":"1 平-234平","shop_max_area":"234","brand_location":"123","tag":["",""]},{"brand_id":"1816","industry_id":"50","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5895db670c79d.jpg","brand_name":"大邻洞首尔炸鸡创业店","invest_amount":"11.98","shop_area":"20 平-30平","shop_max_area":"30","brand_location":"上海","tag":["",""]}]},{"name":"饮","data":[{"brand_id":"1346","industry_id":"62","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5875d4ac53982.png","brand_name":"茶桔便 hey juice","invest_amount":"18.3","shop_area":"10 平-30平","shop_max_area":"30","brand_location":"杭州","tag":["认证","官方"]},{"brand_id":"1345","industry_id":"62","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b1a556912a.png","brand_name":"一点点茶饮","invest_amount":"10.3","shop_area":"0 平-50平","shop_max_area":"50","brand_location":"上海","tag":["",""]},{"brand_id":"337","industry_id":"62","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5714b7e3a528e.jpg","brand_name":"咖啡零点吧自助现磨咖啡","invest_amount":"5.8","shop_area":"1 平以上","shop_max_area":"0","brand_location":"北京","tag":["",""]},{"brand_id":"1333","industry_id":"62","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5876ee0798729.png","brand_name":"甘茶度","invest_amount":"18","shop_area":"10 平以上","shop_max_area":"0","brand_location":"杭州","tag":["认证","官方"]},{"brand_id":"1317","industry_id":"62","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5719b08294c22.png","brand_name":"royaltea皇茶","invest_amount":"19.25","shop_area":"20 平-80平","shop_max_area":"80","brand_location":"广州","tag":["",""]}]},{"name":"甜品","data":[{"brand_id":"2079","industry_id":"67","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57c7840c0b19f.png","brand_name":"薄荷猫冻酸奶","invest_amount":"14.54","shop_area":"0 平-60平","shop_max_area":"60","brand_location":"北京","tag":["",""]},{"brand_id":"2355","industry_id":"67","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/587f1e993a008.jpg","brand_name":"REMICONE乌云冰淇淋标准店","invest_amount":"16.8","shop_area":"25 平-30平","shop_max_area":"30","brand_location":"上海","tag":["",""]},{"brand_id":"2354","industry_id":"67","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/587f1eec4db39.jpg","brand_name":"REMICONE乌云冰淇淋mini店","invest_amount":"16.8","shop_area":"10 平-15平","shop_max_area":"15","brand_location":"上海","tag":["",""]},{"brand_id":"1796","industry_id":"67","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58a677632c9ee.jpg","brand_name":"DF冰淇淋","invest_amount":"43.72","shop_area":"20 平以上","shop_max_area":"0","brand_location":"北京","tag":["",""]},{"brand_id":"1898","industry_id":"67","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571c39d8302ac.jpg","brand_name":"面包好了","invest_amount":"32","shop_area":"50 平以上","shop_max_area":"0","brand_location":"广州","tag":["",""]}]},{"name":"零售","data":[{"brand_id":"1676","industry_id":"70","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b3e3848440.jpg","brand_name":"良品铺子","invest_amount":"42.5","shop_area":"0 平-50平","shop_max_area":"50","brand_location":"武汉","tag":["",""]},{"brand_id":"426","industry_id":"70","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571874aff2827.png","brand_name":"全家便利店","invest_amount":"56","shop_area":"100 平以上","shop_max_area":"0","brand_location":"上海","tag":["",""]},{"brand_id":"420","industry_id":"70","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57187690b6837.png","brand_name":"罗森便利店","invest_amount":"20","shop_area":"40 平-120平","shop_max_area":"120","brand_location":"上海","tag":["",""]},{"brand_id":"381","industry_id":"70","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571886b3aa0ca.png","brand_name":"名创优品","invest_amount":"90","shop_area":"200 平以上","shop_max_area":"0","brand_location":"广州","tag":["",""]},{"brand_id":"384","industry_id":"70","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571885a717a39.jpg","brand_name":"哎呀呀生活馆","invest_amount":"31","shop_area":"80 平以上","shop_max_area":"0","brand_location":"广州","tag":["",""]}]},{"name":"美容保健","data":[{"brand_id":"1642","industry_id":"79","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b1be550836.jpg","brand_name":"克丽缇娜","invest_amount":"37.5","shop_area":"150 平以上","shop_max_area":"0","brand_location":"上海","tag":["",""]},{"brand_id":"411","industry_id":"79","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571879ba7f4f6.jpg","brand_name":"美丽妈妈","invest_amount":"30","shop_area":"150 平-200平","shop_max_area":"200","brand_location":"上海","tag":["",""]},{"brand_id":"410","industry_id":"79","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571879d2291bb.jpg","brand_name":"骄阳兰多产后修复","invest_amount":"29.48","shop_area":"120 平以上","shop_max_area":"0","brand_location":"重庆","tag":["",""]},{"brand_id":"1645","industry_id":"79","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b1bbf027f9.jpg","brand_name":"秀域古方减肥","invest_amount":"72.5","shop_area":"150 平以上","shop_max_area":"0","brand_location":"上海","tag":["",""]},{"brand_id":"1643","industry_id":"79","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b1bd91ab74.jpg","brand_name":"植秀堂","invest_amount":"11.98","shop_area":"100 平以上","shop_max_area":"0","brand_location":"青岛","tag":["",""]}]},{"name":"生活服务","data":[{"brand_id":"2480","industry_id":"83","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/59702a060708f.jpg","brand_name":"滴滴打车","invest_amount":"123","shop_area":"21 平-23平","shop_max_area":"23","brand_location":"13","tag":["认证","比目大神"]},{"brand_id":"1631","industry_id":"83","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b1cbce3216.jpg","brand_name":"悠游堂亲子乐园","invest_amount":"70.05","shop_area":"250 平-500平","shop_max_area":"500","brand_location":"上海","tag":["",""]},{"brand_id":"1640","industry_id":"83","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b1c46ef65b.png","brand_name":"圣宠宠物","invest_amount":"9.9","shop_area":"50 平-150平","shop_max_area":"150","brand_location":"北京","tag":["",""]},{"brand_id":"2071","industry_id":"83","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5792ed42e1c8c.png","brand_name":"金色童年儿童摄影","invest_amount":"41.86","shop_area":"120 平-300平","shop_max_area":"300","brand_location":"济南","tag":["",""]},{"brand_id":"430","industry_id":"83","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571874240f2aa.png","brand_name":"象王干洗","invest_amount":"36","shop_area":"50 平-60平","shop_max_area":"60","brand_location":"北京","tag":["",""]}]},{"name":"教育","data":[{"brand_id":"2056","industry_id":"92","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/576c9b33534b7.jpg","brand_name":"东方童艺术教育","invest_amount":"45","shop_area":"260 平以上","shop_max_area":"0","brand_location":"北京","tag":["",""]},{"brand_id":"440","industry_id":"92","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571872ae63d18.png","brand_name":"爱贝国际少儿英语","invest_amount":"145","shop_area":"300 平以上","shop_max_area":"0","brand_location":"上海","tag":["",""]},{"brand_id":"452","industry_id":"92","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57186f0762bae.jpg","brand_name":"蒙特梭利早教","invest_amount":"119.4","shop_area":"400 平以上","shop_max_area":"0","brand_location":"北京","tag":["",""]},{"brand_id":"460","industry_id":"92","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57186be5c029c.jpg","brand_name":"GYMBABY","invest_amount":"100","shop_area":"500 平以上","shop_max_area":"0","brand_location":"北京","tag":["",""]},{"brand_id":"458","industry_id":"92","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57186c3412b60.jpg","brand_name":"吉的堡少儿英语","invest_amount":"50","shop_area":"350 平以上","shop_max_area":"0","brand_location":"上海","tag":["",""]}]}]
     * data : []
     */

    private int exe_success;
    private List<BrandIndustryBean> brand_industry;
    private List<?> data;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<BrandIndustryBean> getBrand_industry() {
        return brand_industry;
    }

    public void setBrand_industry(List<BrandIndustryBean> brand_industry) {
        this.brand_industry = brand_industry;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class BrandIndustryBean implements Serializable{
        /**
         * name : 新锐
         * data : [{"brand_id":"2060","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5773ef5b08227.png","brand_name":"友宝自动贩卖机","invest_amount":"2.1","shop_area":"0 平-5平","shop_max_area":"5","brand_location":"深圳","tag":["哈皮","二哈"]},{"brand_id":"2075","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57a82e21a27f7.png","brand_name":"海马体照相馆","invest_amount":"0","shop_area":"0 平-500平","shop_max_area":"500","brand_location":"杭州","tag":["",""]},{"brand_id":"338","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5763c0d591bad.png","brand_name":"e袋洗互联网洗衣","invest_amount":"80","shop_area":"200 平以上","shop_max_area":"0","brand_location":"北京","tag":["",""]},{"brand_id":"352","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5714e05f0354b.png","brand_name":"QC快发快剪","invest_amount":"5.88","shop_area":"12 平-15平","shop_max_area":"15","brand_location":"杭州","tag":["",""]},{"brand_id":"2038","industry_id":"16","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/574fa09f1e081.png","brand_name":"网鱼网咖","invest_amount":"150","shop_area":"0 平-360平","shop_max_area":"360","brand_location":"上海","tag":["",""]}]
         */

        private String name;
        private List<DataBean> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * brand_id : 2060
             * industry_id : 16
             * brand_logo : http://v30.bimuwang.com/upload/brandLogo/5773ef5b08227.png
             * brand_name : 友宝自动贩卖机
             * invest_amount : 2.1
             * shop_area : 0 平-5平
             * shop_max_area : 5
             * brand_location : 深圳
             * tag : ["哈皮","二哈"]
             */

            private String brand_id;
            private String industry_id;
            private String brand_logo;
            private String brand_name;
            private String invest_amount;
            private String shop_area;
            private String shop_max_area;
            private String brand_location;
            private List<String> tag;

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getIndustry_id() {
                return industry_id;
            }

            public void setIndustry_id(String industry_id) {
                this.industry_id = industry_id;
            }

            public String getBrand_logo() {
                return brand_logo;
            }

            public void setBrand_logo(String brand_logo) {
                this.brand_logo = brand_logo;
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

            public String getShop_area() {
                return shop_area;
            }

            public void setShop_area(String shop_area) {
                this.shop_area = shop_area;
            }

            public String getShop_max_area() {
                return shop_max_area;
            }

            public void setShop_max_area(String shop_max_area) {
                this.shop_max_area = shop_max_area;
            }

            public String getBrand_location() {
                return brand_location;
            }

            public void setBrand_location(String brand_location) {
                this.brand_location = brand_location;
            }

            public List<String> getTag() {
                return tag;
            }

            public void setTag(List<String> tag) {
                this.tag = tag;
            }
        }
    }
}
