package com.can.bimuprojects.Module.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by can on 2017/4/24.
 * 专题文章列表
 */

public class GetSpecialArticleResponse {

    /**
     * method : get_article_list_ad
     * timestamp : 1493035548
     * status : 200
     * error :
     * times_used : 1
     * response : {"exe_success":1,"cards":[{"timestamp":"1489051304","article_id":"2473","author_id":"2373","author_image":"http://v30.bimuwang.com/upload/userLogo/1476088579u2373.jpg","author_nickname":"首无","text":"\u201c摇滚三国\u201d真实探店 | 简单来说，以\u201c三国\u201d为背景的都不简单","brand_tag":"摇滚三国麻辣e站","brand_tag_id":"2063","article_tag":"考察日记","article_tag_id":"2","classify_tag":"麻辣烫","classify_tag_id":"55","article_image_urls":["http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e03e6bf3.gif","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e30065b8.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e4044355.jpg"],"like_count":"15","has_followed":0,"has_praised":0,"sort_weight":"0","comment_count":"7","read_num":2517,"article_summary":"\u201c摇滚三国\u201d真实探店 | 简单来说，以\u201c三国\u201d为背景的都不简单","rticle_html":"不知道大家是不是都跟我一样，吃到又便宜又好吃的东西比吃到又贵又好吃的东西能多高兴个一百倍左右。双11虽然已经是去年的事但蚂蚁花呗还没有还完的肯定不止我一个，所有能用钱解决的问题，基本都解决不了了。不过本着\"手可以剁 但恩格尔系数不能掉\"的人生信条，平时还是要花点小钱，吃个痛快，比如吃一顿人均20的麻"},{"timestamp":"1489048482","article_id":"2472","author_id":"1890","author_image":"http://v30.bimuwang.com/upload/userLogo/1487417372u1890.jpg","author_nickname":"小田","text":"文艺而不失霸道，一碗麻辣烫把所有不快都吃掉","brand_tag":"摇滚三国麻辣e站","brand_tag_id":"2063","article_tag":"考察日记","article_tag_id":"2","classify_tag":"麻辣烫","classify_tag_id":"55","article_image_urls":["http://v30.bimuwang.com/upload/articlePictures/thumb_58c11252b569b.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11260433a5.gif","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1126b32ec9.jpg"],"like_count":"2","has_followed":0,"has_praised":0,"sort_weight":"0","comment_count":"2","read_num":1671,"article_summary":"文艺而不失霸道，一碗麻辣烫把所有不快都吃掉","rticle_html":"日剧《昨夜的咖喱，明日的面包》里有句台词是：\u201c无论多么悲伤都要好好吃饭。\u201d在我们需要安慰的时刻，食物永远是快乐与元气的来源。 记着上学的时候最开心的就是和同学一起去学校对面的馆子吃麻辣烫，从学生时代的我们变成现在很要好的朋友，依旧记着那时大汗淋漓吃着麻辣烫的我们。 吃的多了也就有了一番心得，好的麻辣"},{"timestamp":"1489045458","article_id":"2471","author_id":"1894","author_image":"http://v30.bimuwang.com/upload/userLogo/1481876182u1894.jpg","author_nickname":"谷今","text":"\u201c摇滚三国\u201d真实探店 | 这家麻辣烫，竟让我吃出了火锅的感觉","brand_tag":null,"brand_tag_id":"0","article_tag":"考察日记","article_tag_id":"2","classify_tag":null,"classify_tag_id":"0","article_image_urls":["http://v30.bimuwang.com/upload/articlePictures/thumb_58c1076a73fe9.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1078b14b40.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1079fc9399.jpg"],"like_count":"0","has_followed":0,"has_praised":0,"sort_weight":"0","comment_count":"1","read_num":1173,"article_summary":"\u201c摇滚三国\u201d真实探店 | 这家麻辣烫，竟让我吃出了火锅的感觉","rticle_html":"都说麻辣烫是一个人的火锅，而火锅是一群人的麻辣烫，但说到底麻辣烫跟火锅还是有区别的，比起火锅还要一步步的涮，直接一大碗煮好端上的麻辣烫可就简单多了。 应该说火锅跟麻辣烫各有各的好吧，但这家\u201c摇滚三国麻辣烫\u201d却让我吃出了火锅的感觉，因为它在很多细节方面更像是火锅而不是麻辣烫。 先说一下装修，摇滚三国麻"}]}
     */

    private String method;
    private int timestamp;
    private int status;
    private String error;
    private int times_used;
    private ResponseBean response;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getTimes_used() {
        return times_used;
    }

    public void setTimes_used(int times_used) {
        this.times_used = times_used;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }
    public static class ResponseBean {
        /**
         * exe_success : 1
         * cards : [{"timestamp":"1489051304","article_id":"2473","author_id":"2373","author_image":"http://v30.bimuwang.com/upload/userLogo/1476088579u2373.jpg","author_nickname":"首无","text":"\u201c摇滚三国\u201d真实探店 | 简单来说，以\u201c三国\u201d为背景的都不简单","brand_tag":"摇滚三国麻辣e站","brand_tag_id":"2063","article_tag":"考察日记","article_tag_id":"2","classify_tag":"麻辣烫","classify_tag_id":"55","article_image_urls":["http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e03e6bf3.gif","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e30065b8.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e4044355.jpg"],"like_count":"15","has_followed":0,"has_praised":0,"sort_weight":"0","comment_count":"7","read_num":2517,"article_summary":"\u201c摇滚三国\u201d真实探店 | 简单来说，以\u201c三国\u201d为背景的都不简单","rticle_html":"不知道大家是不是都跟我一样，吃到又便宜又好吃的东西比吃到又贵又好吃的东西能多高兴个一百倍左右。双11虽然已经是去年的事但蚂蚁花呗还没有还完的肯定不止我一个，所有能用钱解决的问题，基本都解决不了了。不过本着\"手可以剁 但恩格尔系数不能掉\"的人生信条，平时还是要花点小钱，吃个痛快，比如吃一顿人均20的麻"},{"timestamp":"1489048482","article_id":"2472","author_id":"1890","author_image":"http://v30.bimuwang.com/upload/userLogo/1487417372u1890.jpg","author_nickname":"小田","text":"文艺而不失霸道，一碗麻辣烫把所有不快都吃掉","brand_tag":"摇滚三国麻辣e站","brand_tag_id":"2063","article_tag":"考察日记","article_tag_id":"2","classify_tag":"麻辣烫","classify_tag_id":"55","article_image_urls":["http://v30.bimuwang.com/upload/articlePictures/thumb_58c11252b569b.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11260433a5.gif","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1126b32ec9.jpg"],"like_count":"2","has_followed":0,"has_praised":0,"sort_weight":"0","comment_count":"2","read_num":1671,"article_summary":"文艺而不失霸道，一碗麻辣烫把所有不快都吃掉","rticle_html":"日剧《昨夜的咖喱，明日的面包》里有句台词是：\u201c无论多么悲伤都要好好吃饭。\u201d在我们需要安慰的时刻，食物永远是快乐与元气的来源。 记着上学的时候最开心的就是和同学一起去学校对面的馆子吃麻辣烫，从学生时代的我们变成现在很要好的朋友，依旧记着那时大汗淋漓吃着麻辣烫的我们。 吃的多了也就有了一番心得，好的麻辣"},{"timestamp":"1489045458","article_id":"2471","author_id":"1894","author_image":"http://v30.bimuwang.com/upload/userLogo/1481876182u1894.jpg","author_nickname":"谷今","text":"\u201c摇滚三国\u201d真实探店 | 这家麻辣烫，竟让我吃出了火锅的感觉","brand_tag":null,"brand_tag_id":"0","article_tag":"考察日记","article_tag_id":"2","classify_tag":null,"classify_tag_id":"0","article_image_urls":["http://v30.bimuwang.com/upload/articlePictures/thumb_58c1076a73fe9.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1078b14b40.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c1079fc9399.jpg"],"like_count":"0","has_followed":0,"has_praised":0,"sort_weight":"0","comment_count":"1","read_num":1173,"article_summary":"\u201c摇滚三国\u201d真实探店 | 这家麻辣烫，竟让我吃出了火锅的感觉","rticle_html":"都说麻辣烫是一个人的火锅，而火锅是一群人的麻辣烫，但说到底麻辣烫跟火锅还是有区别的，比起火锅还要一步步的涮，直接一大碗煮好端上的麻辣烫可就简单多了。 应该说火锅跟麻辣烫各有各的好吧，但这家\u201c摇滚三国麻辣烫\u201d却让我吃出了火锅的感觉，因为它在很多细节方面更像是火锅而不是麻辣烫。 先说一下装修，摇滚三国麻"}]
         */
        @JsonIgnoreProperties(value={"exe_success"})
        private int exe_success;
        private List<CardsBean> cards;

        public int getExe_success() {
            return exe_success;
        }

        public void setExe_success(int exe_success) {
            this.exe_success = exe_success;
        }

        public List<CardsBean> getCards() {
            return cards;
        }

        public void setCards(List<CardsBean> cards) {
            this.cards = cards;
        }

        public static class CardsBean {
            /**
             * timestamp : 1489051304
             * article_id : 2473
             * author_id : 2373
             * author_image : http://v30.bimuwang.com/upload/userLogo/1476088579u2373.jpg
             * author_nickname : 首无
             * text : “摇滚三国”真实探店 | 简单来说，以“三国”为背景的都不简单
             * brand_tag : 摇滚三国麻辣e站
             * brand_tag_id : 2063
             * article_tag : 考察日记
             * article_tag_id : 2
             * classify_tag : 麻辣烫
             * classify_tag_id : 55
             * article_image_urls : ["http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e03e6bf3.gif","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e30065b8.jpg","http://v30.bimuwang.com/upload/articlePictures/thumb_58c11e4044355.jpg"]
             * like_count : 15
             * has_followed : 0
             * has_praised : 0
             * sort_weight : 0
             * comment_count : 7
             * read_num : 2517
             * article_summary : “摇滚三国”真实探店 | 简单来说，以“三国”为背景的都不简单
             * rticle_html : 不知道大家是不是都跟我一样，吃到又便宜又好吃的东西比吃到又贵又好吃的东西能多高兴个一百倍左右。双11虽然已经是去年的事但蚂蚁花呗还没有还完的肯定不止我一个，所有能用钱解决的问题，基本都解决不了了。不过本着"手可以剁 但恩格尔系数不能掉"的人生信条，平时还是要花点小钱，吃个痛快，比如吃一顿人均20的麻
             */

            private String timestamp;
            private String article_id;
            private String author_id;
            private String author_image;
            private String author_nickname;
            private String text;
            private String brand_tag;
            private String brand_tag_id;
            private String article_tag;
            private String article_tag_id;
            private String classify_tag;
            private String classify_tag_id;
            private String like_count;
            private int has_followed;
            private int has_praised;
            private String sort_weight;
            private String comment_count;
            private int read_num;
            private String article_summary;
            private String rticle_html;
            private List<String> article_image_urls;

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getArticle_id() {
                return article_id;
            }

            public void setArticle_id(String article_id) {
                this.article_id = article_id;
            }

            public String getAuthor_id() {
                return author_id;
            }

            public void setAuthor_id(String author_id) {
                this.author_id = author_id;
            }

            public String getAuthor_image() {
                return author_image;
            }

            public void setAuthor_image(String author_image) {
                this.author_image = author_image;
            }

            public String getAuthor_nickname() {
                return author_nickname;
            }

            public void setAuthor_nickname(String author_nickname) {
                this.author_nickname = author_nickname;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getBrand_tag() {
                return brand_tag;
            }

            public void setBrand_tag(String brand_tag) {
                this.brand_tag = brand_tag;
            }

            public String getBrand_tag_id() {
                return brand_tag_id;
            }

            public void setBrand_tag_id(String brand_tag_id) {
                this.brand_tag_id = brand_tag_id;
            }

            public String getArticle_tag() {
                return article_tag;
            }

            public void setArticle_tag(String article_tag) {
                this.article_tag = article_tag;
            }

            public String getArticle_tag_id() {
                return article_tag_id;
            }

            public void setArticle_tag_id(String article_tag_id) {
                this.article_tag_id = article_tag_id;
            }

            public String getClassify_tag() {
                return classify_tag;
            }

            public void setClassify_tag(String classify_tag) {
                this.classify_tag = classify_tag;
            }

            public String getClassify_tag_id() {
                return classify_tag_id;
            }

            public void setClassify_tag_id(String classify_tag_id) {
                this.classify_tag_id = classify_tag_id;
            }

            public String getLike_count() {
                return like_count;
            }

            public void setLike_count(String like_count) {
                this.like_count = like_count;
            }

            public int getHas_followed() {
                return has_followed;
            }

            public void setHas_followed(int has_followed) {
                this.has_followed = has_followed;
            }

            public int getHas_praised() {
                return has_praised;
            }

            public void setHas_praised(int has_praised) {
                this.has_praised = has_praised;
            }

            public String getSort_weight() {
                return sort_weight;
            }

            public void setSort_weight(String sort_weight) {
                this.sort_weight = sort_weight;
            }

            public String getComment_count() {
                return comment_count;
            }

            public void setComment_count(String comment_count) {
                this.comment_count = comment_count;
            }

            public int getRead_num() {
                return read_num;
            }

            public void setRead_num(int read_num) {
                this.read_num = read_num;
            }

            public String getArticle_summary() {
                return article_summary;
            }

            public void setArticle_summary(String article_summary) {
                this.article_summary = article_summary;
            }

            public String getRticle_html() {
                return rticle_html;
            }

            public void setRticle_html(String rticle_html) {
                this.rticle_html = rticle_html;
            }

            public List<String> getArticle_image_urls() {
                return article_image_urls;
            }

            public void setArticle_image_urls(List<String> article_image_urls) {
                this.article_image_urls = article_image_urls;
            }
        }
    }
}
