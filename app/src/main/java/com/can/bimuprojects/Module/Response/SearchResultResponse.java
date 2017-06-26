package com.can.bimuprojects.Module.Response;

import java.util.List;

/**
 * Created by can on 2017/4/9.
 */

public class SearchResultResponse {

    /**
     * exe_success : 1
     * total : 20
     * data : [{"brand_id":"1879","brand_name":"优芙丝冰淇淋","classify_id":"46","industry_id":"18","invest_amount":"8","brand_location":"北京","shop_area":"0 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_571da7534fb04.png","brand_background":"","follow":"0"},{"brand_id":"2368","brand_name":"撒露欧洲进口冻酸奶","classify_id":"69","industry_id":"67","invest_amount":"49.2","brand_location":"北京","shop_area":"25 平-50平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f194a95dc0.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f195b87c84.jpg","follow":"0"},{"brand_id":"2362","brand_name":"YOBA优芭酸奶冰淇淋小型店","classify_id":"46","industry_id":"67","invest_amount":"14.8","brand_location":"上海","shop_area":"0 平-20平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f1d00a0b3e.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f1d06ca3d0.jpg","follow":"0"},{"brand_id":"2363","brand_name":"YOBA优芭酸奶冰淇淋中型店","classify_id":"46","industry_id":"67","invest_amount":"19.8","brand_location":"上海","shop_area":"20 平-40平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f1cd78759b.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f1cdb3516b.jpg","follow":"0"},{"brand_id":"2364","brand_name":"YOBA优芭酸奶冰淇淋大型店","classify_id":"46","industry_id":"67","invest_amount":"24.8","brand_location":"上海","shop_area":"40 平-80平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f1ca125a9c.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f1cab65ecd.jpg","follow":"0"},{"brand_id":"2365","brand_name":"YOBA优芭酸奶冰淇淋特大店","classify_id":"46","industry_id":"67","invest_amount":"29.8","brand_location":"上海","shop_area":"80 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f1c703ae75.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f1c763ca49.jpg","follow":"0"},{"brand_id":"2369","brand_name":"香乳以沫酸奶吧基本店","classify_id":"69","industry_id":"67","invest_amount":"1.98","brand_location":"济南","shop_area":"10 平-30平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_5880858f40f02.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5880858c3727f.jpg","follow":"0"},{"brand_id":"2370","brand_name":"香乳以沫酸奶吧标准店","classify_id":"69","industry_id":"67","invest_amount":"2.68","brand_location":"济南","shop_area":"30 平-60平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_5880856096a52.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/588085635964f.jpg","follow":"0"},{"brand_id":"2115","brand_name":"姑奶奶鱼香茄子厨坊","classify_id":"59","industry_id":"50","invest_amount":"25.3","brand_location":"上海","shop_area":"200 平-500平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_5880622a96c40.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58806227d5fca.jpg","follow":"0"},{"brand_id":"2371","brand_name":"香乳以沫酸奶吧豪华店","classify_id":"69","industry_id":"67","invest_amount":"3.68","brand_location":"济南","shop_area":"60 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_58808540d0980.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/5880853d5bdf6.jpg","follow":"0"},{"brand_id":"2373","brand_name":"优格花园冻酸奶标准店","classify_id":"69","industry_id":"67","invest_amount":"16.2","brand_location":"济南","shop_area":"30 平-60平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f15d74cd87.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f15de9eea9.jpg","follow":"0"},{"brand_id":"2374","brand_name":"优格花园冻酸奶豪华店","classify_id":"69","industry_id":"67","invest_amount":"21.2","brand_location":"济南","shop_area":"60 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f159998b68.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f15a42e50c.jpg","follow":"0"},{"brand_id":"2375","brand_name":"yeh!非自助冻酸奶小型店","classify_id":"69","industry_id":"67","invest_amount":"32","brand_location":"上海","shop_area":"15 平-30平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f1537ed6c9.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f154de5f99.jpg","follow":"0"},{"brand_id":"2376","brand_name":"yeh!自助冻酸奶小型店","classify_id":"69","industry_id":"67","invest_amount":"42","brand_location":"上海","shop_area":"25 平-40平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f1058ab64f.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f106601c6d.jpg","follow":"0"},{"brand_id":"2377","brand_name":"yeh!自助冻酸奶标准店","classify_id":"69","industry_id":"67","invest_amount":"60","brand_location":"上海","shop_area":"41 平-80平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f10075cdc6.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f100d50aef.jpg","follow":"0"},{"brand_id":"2379","brand_name":"臻好时冻酸奶标准店","classify_id":"69","industry_id":"67","invest_amount":"7.48","brand_location":"济南","shop_area":"30 平-100平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f0faa7be4b.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f0fade1777.jpg","follow":"0"},{"brand_id":"2380","brand_name":"臻好时冻酸奶世家店","classify_id":"69","industry_id":"67","invest_amount":"10.98","brand_location":"济南","shop_area":"100 平-200平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587f0f7c53615.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587f0f833d31c.jpg","follow":"0"},{"brand_id":"2427","brand_name":"阿姨奶茶","classify_id":"63","industry_id":"62","invest_amount":"6.8","brand_location":"上海","shop_area":"10 平-50平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587ee4b1267bc.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587ee4b687ae8.jpg","follow":"0"},{"brand_id":"2436","brand_name":"可浓奶茶","classify_id":"63","industry_id":"62","invest_amount":"13.08","brand_location":"杭州","shop_area":"10 平-50平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_58817de1c537d.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58817de5300f1.jpg","follow":"0"},{"brand_id":"2443","brand_name":"兰沁园港式奶茶","classify_id":"63","industry_id":"62","invest_amount":"11.88","brand_location":"上海","shop_area":"10 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/thumb_587e13b8caf27.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/587e13bca2ef7.jpg","follow":"0"}]
     * category : [{"classify_id":"46","classify_name":"冰淇淋","classify_icon":"http://v30.bimuwang.com/upload/classifyIcon/5874ced6c5d03.jpg"},{"classify_id":"59","classify_name":"中式快餐","classify_icon":"http://v30.bimuwang.com/upload/classifyIcon/587595fc73ce2.jpg"},{"classify_id":"63","classify_name":"奶茶","classify_icon":"http://v30.bimuwang.com/upload/classifyIcon/5874cf9a6e69e.jpg"},{"classify_id":"69","classify_name":"冻酸奶","classify_icon":"http://v30.bimuwang.com/upload/classifyIcon/5874ceea03156.jpg"}]
     */

    private int exe_success;
    private int total;
    private List<DataBean> data;
    private List<CategoryBean> category;

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

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public static class DataBean {
        /**
         * brand_id : 1879
         * brand_name : 优芙丝冰淇淋
         * classify_id : 46
         * industry_id : 18
         * invest_amount : 8
         * brand_location : 北京
         * shop_area : 0 平以上
         * brand_logo : http://v30.bimuwang.com/upload/brandLogo/thumb_571da7534fb04.png
         * brand_background :
         * follow : 0
         */

        private String brand_id;
        private String brand_name;
        private String classify_id;
        private String industry_id;
        private String invest_amount;
        private String brand_location;
        private String shop_area;
        private String brand_logo;
        private String brand_background;
        private String follow;

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

        public String getBrand_background() {
            return brand_background;
        }

        public void setBrand_background(String brand_background) {
            this.brand_background = brand_background;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }
    }

    public static class CategoryBean {
        /**
         * classify_id : 46
         * classify_name : 冰淇淋
         * classify_icon : http://v30.bimuwang.com/upload/classifyIcon/5874ced6c5d03.jpg
         */

        private String classify_id;
        private String classify_name;
        private String classify_icon;

        public String getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(String classify_id) {
            this.classify_id = classify_id;
        }

        public String getClassify_name() {
            return classify_name;
        }

        public void setClassify_name(String classify_name) {
            this.classify_name = classify_name;
        }

        public String getClassify_icon() {
            return classify_icon;
        }

        public void setClassify_icon(String classify_icon) {
            this.classify_icon = classify_icon;
        }
    }
}
