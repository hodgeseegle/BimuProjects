package com.can.bimuprojects.Module.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by can on 2017/4/8.
 *首页数据类
 */

public class HomePagerResponse implements Serializable{


    /**
     * exe_success : 1
     * list : [{"type":1,"img_url":"http://v30.bimuwang.com/upload/adPicture/58c12937a4ba3.jpg","title":"文艺不失霸道的麻辣烫，能不火吗？","order":[],"id":"316"},{"type":3,"img_url":"http://v30.bimuwang.com/upload/adPicture/58b66b2f5f8a8.jpg","title":"初生牛犊不怕虎，没经验的小白，这样也能迈出创业第一步","order":["http://v30.bimuwang.com/upload/brandLogo/583694d2d1cec.jpg","http://v30.bimuwang.com/upload/brandLogo/585a31e9cb254.png"],"id":"2424"},{"type":3,"img_url":"http://v30.bimuwang.com/upload/adPicture/58b0f35a90a1f.jpg","title":"星巴克，你就不要再尝试做茶饮了吧！","order":["http://v30.bimuwang.com/upload/brandLogo/5876ee0798729.png"],"id":"2418"},{"type":3,"img_url":"http://v30.bimuwang.com/upload/adPicture/58af96b19c116.jpg","title":"夏天快到了我想减肥，你能给我一个300卡路里的菜单吗？","order":[],"id":"2415"}]
     * sliding_img : [{"img_url":"http://v30.bimuwang.com/upload/adPicture/58e85333514db.jpg","type":3,"id":"2570"},{"img_url":"http://v30.bimuwang.com/upload/adPicture/58e899175bf6b.png","type":4,"id":"154"},{"img_url":"http://v30.bimuwang.com/upload/adPicture/58e75be27bdf7.jpg","type":4,"id":"153"},{"img_url":"http://v30.bimuwang.com/upload/adPicture/58be10a345a6f.jpg","type":3,"id":"2410"}]
     * like : [{"type":1,"tid":"1935","tname":"香港驰记面家"},{"type":1,"tid":"2079","tname":"薄荷猫冻酸奶"},{"type":1,"tid":"1346","tname":"茶桔便 hey juice"}]
     * brandlist : [{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","scribe":525},{"brand_id":"2079","brand_name":"薄荷猫冻酸奶","classify_id":"69","invest_amount":"14.54","brand_location":"北京","shop_area":"0 平-60 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/57c7840c0b19f.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589414a5eaf23.jpg","scribe":649},{"brand_id":"1333","brand_name":"甘茶度","classify_id":"65","invest_amount":"18","brand_location":"杭州","shop_area":"10 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5876ee0798729.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589215b302a40.jpg","scribe":903},{"brand_id":"1346","brand_name":"茶桔便 hey juice","classify_id":"63","invest_amount":"18.3","brand_location":"杭州","shop_area":"10 平-30 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5875d4ac53982.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/589cb2386981d.jpg","scribe":916},{"brand_id":"1347","brand_name":"奶茶博士","classify_id":"63","invest_amount":"8.5","brand_location":"杭州","shop_area":"6 平以上","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/571b1a1e16edc.png","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58920ab0e3cd8.jpg","scribe":917},{"brand_id":"1935","brand_name":"香港驰记面家","classify_id":"58","invest_amount":"48","brand_location":"广州","shop_area":"50 平-200 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/5893ff6b36397.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58a55f9beae22.jpg","scribe":753}]
     * inspectionlog : [{"article_id":"2475","article_title":"不想坐飞机买台湾茶，但可以排队买杯一芳水果茶啊！","article_html":"一芳水果茶源自台湾，它们家的水果茶是使用当季采摘的可口水果还有台湾甘蔗熬制的糖水，经过用心复杂的工艺，手作而成，特别适合在夏天饮用，清热解暑。但吃货们馋起来，可不会管你是寒冬还是酷暑，就算排队也要买上一杯。 接下来就跟着比目一起看看一芳在全国生意到底如何。 科学博物馆门前熙熙攘攘的人群 人声鼎沸的妈","look_time":74288,"article_comment_number":"4","article_logo":"http://app.bimuwang.com/upload/articlePictures/58c1279102b72.gif","order":[]},{"article_id":"2548","article_title":"从一芳转战\u201c到此一鱿\u201d，亲自考察","article_html":"在比目上找项目有一段时间了，之前想加盟一芳水果茶，就是很多人关注的那个，但现在我改变主意了，更想做这个鱿鱼品牌。 之前去台湾旅游时，在台中逢甲夜市吃过这家叫\u201c到此一鱿\u201d的铁板鱿鱼，回来后一直念念不忘，这是当时拍的一些照片，跟各位比友分享一下。 操作很简单，就是把整只鱿鱼放进超高温设备，十几秒就好，剪","look_time":355,"article_comment_number":"4","article_logo":"http://app.bimuwang.com/upload/articlePictures/58d39fb3002b7.jpg","order":["http://v30.bimuwang.com/upload/brandLogo/58ae5376468d3.jpg"]},{"article_id":"2055","article_title":"木九十眼镜，渠道做好了服务是不是该改善下？亲身经历","article_html":"木九十眼镜，渠道做好了服务是不是该改善下？ 亲身经历，半个月前买了木九十的眼镜，取镜时店员问都不问，也不调鼻托直接让我取了走，说让我自己回家掰。 实在没办法就去就近其他家的木九十眼镜让帮忙调一下，各种不耐烦，把我眼镜各种死劲掰。最后镜架弄掉漆，眼镜掰变型了。 这应该怪质量差？ 打售后服务电话，硬是没","look_time":2477,"article_comment_number":"4","article_logo":"","order":[]},{"article_id":"1780","article_title":"中午路过中山公园的fly juice看了一下","article_html":"中午路过中山公园的fly juice看了一下，门店在地下一层的美食广场，门店很新潮，团购了一杯葡萄仔，10元外加8块换了个玻璃杯，口味还是挺不错的，并不难喝。待了一会只有另一个顾客买了两杯。附近奶茶倒是在排队。因为有事就先走了，改天再来蹲点考察一下一天的总人流和销量。上海现在有好几家店了吧    ","look_time":1976,"article_comment_number":"21","article_logo":"http://app.bimuwang.com/upload/articlePictures/1474957249u1856.jpg","order":["http://v30.bimuwang.com/upload/brandLogo/571986176a99a.jpg"]},{"article_id":"2079","article_title":"不拍一个百元以上的证件照都不好意思拿出来！这些\u201c最美证件照\u201d们哪个值得加盟？","article_html":"这些\u201c最美证件照\u201d们哪个值得加盟？ 看准大学生市场，想做现在很火的最美证件照摄影工作室。 现在所谓的最美证件照都是小本投资，100块钱就是吃顿饭的钱，90后接受程度最佳。自已总结了下，觉得考察这些品牌主要是这四个点：服装，领子够不够简洁；化妆，化妆够不够自然大方；修图，修过的照片够不够理想。环境，在","look_time":3814,"article_comment_number":"10","article_logo":"http://app.bimuwang.com/upload/articlePictures/1480326187art1902.jpg","order":["http://v30.bimuwang.com/upload/brandLogo/57a82e21a27f7.png","http://v30.bimuwang.com/upload/brandLogo/5792ed42e1c8c.png"]}]
     */

    private int exe_success;
    private List<ListBean> list;
    private List<SlidingImgBean> sliding_img;
    private List<LikeBean> like;
    private List<BrandlistBean> brandlist;
    private List<InspectionlogBean> inspectionlog;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<SlidingImgBean> getSliding_img() {
        return sliding_img;
    }

    public void setSliding_img(List<SlidingImgBean> sliding_img) {
        this.sliding_img = sliding_img;
    }

    public List<LikeBean> getLike() {
        return like;
    }

    public void setLike(List<LikeBean> like) {
        this.like = like;
    }

    public List<BrandlistBean> getBrandlist() {
        return brandlist;
    }

    public void setBrandlist(List<BrandlistBean> brandlist) {
        this.brandlist = brandlist;
    }

    public List<InspectionlogBean> getInspectionlog() {
        return inspectionlog;
    }

    public void setInspectionlog(List<InspectionlogBean> inspectionlog) {
        this.inspectionlog = inspectionlog;
    }

    public static class ListBean {
        public ListBean() {
        }

        /**
         * type : 1
         * img_url : http://v30.bimuwang.com/upload/adPicture/58c12937a4ba3.jpg
         * title : 文艺不失霸道的麻辣烫，能不火吗？
         * order : []
         * id : 316
         */


        private int type;
        private String img_url;
        private String title;
        private String id;
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        @JsonIgnoreProperties(ignoreUnknown=true)
        private List<?> order;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<?> getOrder() {
            return order;
        }
        public void setOrder(List<?> order) {
            this.order = order;
        }
    }

    public static class SlidingImgBean {
        /**
         * img_url : http://v30.bimuwang.com/upload/adPicture/58e85333514db.jpg
         * type : 3
         * id : 2570
         */

        private String img_url;
        private int type;
        private String id;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class LikeBean {
        /**
         * type : 1
         * tid : 1935
         * tname : 香港驰记面家
         */

        private int type;
        private String tid;
        private String tname;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }
    }

    public static class BrandlistBean implements Serializable{


        /**
         * name : 热销饮料
         * data : [{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479},{"brand_id":"2479","brand_name":"王子品牌","classify_id":"80","invest_amount":"232","brand_location":"北京","shop_area":"123 平-123 平","brand_logo":"http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg","brand_background":"http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg","autof":"","autos":"","scribe":479}]
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

        public static class DataBean implements Serializable{
            /**
             * brand_id : 2479
             * brand_name : 王子品牌
             * classify_id : 80
             * invest_amount : 232
             * brand_location : 北京
             * shop_area : 123 平-123 平
             * brand_logo : http://v30.bimuwang.com/upload/brandLogo/58c8e579750f7.jpg
             * brand_background : http://v30.bimuwang.com/upload/brandLogo/58c8e5acabd4c.jpg
             * autof :
             * autos :
             * scribe : 479
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

    public static class InspectionlogBean {

        /**
         * article_id : 2956
         * article_title : 文能猪
         * article_html : 您lolX5我用咯做礼物无锡我虚拟印象作息乌云1同意做一组YY首先按下面的步骤来实现一个简单的上传功能。1 创建Web项目，命名为JQueryUploadDemo，从官网上下载最新的版本解压后添加到项目中。2 在项目中添加UploadHandler.ashx文件用来处理文件的上传。3 在项目图:            滴滴打车  投资金额：123万      总部：13  去咨询                  首帖，申请精华！本人一直从事汽                    Wowwoo
         * look_time : 97482
         * article_comment_number : 0
         * type : 2
         * article_logo : ["http://v30.bimuwang.com/upload/articlePictures/1498724103art4189.jpg","http://v30.bimuwang.com/upload/brandLogo/595203765460a.jpg","http://120.24.211.238/upload/articlePictures/577231cfecf89.jpg"]
         * order : [{"bid":"2480","artimg":"http://v30.bimuwang.com/upload/brandLogo/thumb_595203395de9e.jpg"}]
         */

        private String article_id;
        private String article_title;
        private String article_html;
        private String look_time;
        private String article_comment_number;
        private String type;
        private List<String> article_logo;
        private List<OrderBean> order;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public String getArticle_html() {
            return article_html;
        }

        public void setArticle_html(String article_html) {
            this.article_html = article_html;
        }

        public String getLook_time() {
            return look_time;
        }

        public void setLook_time(String look_time) {
            this.look_time = look_time;
        }

        public String getArticle_comment_number() {
            return article_comment_number;
        }

        public void setArticle_comment_number(String article_comment_number) {
            this.article_comment_number = article_comment_number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getArticle_logo() {
            return article_logo;
        }

        public void setArticle_logo(List<String> article_logo) {
            this.article_logo = article_logo;
        }

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * bid : 2480
             * artimg : http://v30.bimuwang.com/upload/brandLogo/thumb_595203395de9e.jpg
             */

            private String bid;
            private String artimg;

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getArtimg() {
                return artimg;
            }

            public void setArtimg(String artimg) {
                this.artimg = artimg;
            }
        }
    }
}

