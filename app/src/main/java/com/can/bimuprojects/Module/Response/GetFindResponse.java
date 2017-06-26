package com.can.bimuprojects.Module.Response;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

/**
 * Created by can on 2017/4/21.
 * 发现页
 */
public class GetFindResponse {

    /**
     * exe_success : 1
     * total : 5
     * data : {"classify_id":"61","classify_name":"街头小吃","classify_icon":"http://v30.bimuwang.com/upload/classifyIcon/5874cfa8370fe.jpg","fans":21504}
     * articledata : [{"article_id":"2365","classify_id":"61","user_id":"552","article_summary":"麦当劳的那么大鸡排，也敢和这几家鸡排专门店相比？","article_html":"当麦当劳开始卖力地推广它的\u201c那么大\u201d鸡排的时候，想做街头小吃鸡排的比友们是否有一种来者不善的感觉？实际上，比友们大可不必为此操心。麦当劳做的是正餐（让你午餐晚餐光吃一份鸡排，你一定接受不了吧？），鸡排我们更加喜欢将其放到街头小吃的范畴。也就是说，麦当劳进入鸡排领域，除了证实了国人对鸡排的接受程度很高","article_comment_number":"4","article_thumbs":"4","look_time":"10w+","userthunm":"0","article":["http://30.bimuwang.com/upload/articlePictures/58aa990dbb4c0.jpeg","http://30.bimuwang.com/upload/articlePictures/58aa9918eb34d.jpeg","http://30.bimuwang.com/upload/articlePictures/58a510b61d762.jpg","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img0.png","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img1.png","http://30.bimuwang.com/upload/articlePictures/58a510beeb509.jpg","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img0.png","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img1.png","http://30.bimuwang.com/upload/articlePictures/58aa94c044c07.jpeg"]},{"article_id":"1304","classify_id":"61","user_id":"377","article_summary":"开个帖子答疑。本人长期在鸡排店兼职","article_html":"开个帖子答疑。本人长期在鸡排店兼职，地址在学校附近，鸡排店也在一个比较好的位置，人流量极大。因为大学嘛。这家店是加盟店，算了，直接告诉你们吧，就是传说中的第一佳大鸡排。我个人觉得，在这里工作半天就能熟悉几乎所有流程。然后你要做的就是收钱，做鸡排。我们这家店也有一些衍生产品，除了鸡排之外还有盐酥鸡，魔","article_comment_number":"12","article_thumbs":"45","look_time":8300,"userthunm":"0","article":["http://120.24.211.238/upload/articlePictures/577dc44e98a77.jpg","http://120.24.211.238/upload/articlePictures/577dc4585224b.jpg"]},{"article_id":"1658","classify_id":"61","user_id":"552","article_summary":"章鱼烧品牌小PK！咕噜咕噜VS弹丸滋地","article_html":"章鱼烧品牌小PK！咕噜咕噜VS弹丸滋地广东两大章鱼烧品牌咕噜咕噜和弹丸滋地一直都在暗暗发力，互相竞争。那么这两个品牌到底谁更强呢？Abby从三个角度帮助大家分析分析：1. 门店数：咕噜咕噜：弹丸滋地：（数字代表该城市拥有几家店）两个都是广东品牌，所以占领本土市场成为很重要的一点。从图上可以看出，咕噜","article_comment_number":"16","article_thumbs":"36","look_time":36223,"userthunm":"0","article":["http://120.24.211.238/upload/articlePictures/57c94d96ed988.png","http://120.24.211.238/upload/articlePictures/57c94db97ac7f.png","http://120.24.211.238/upload/articlePictures/57c94f3775599.png","http://120.24.211.238/upload/articlePictures/57c94f46d2ae6.png","http://120.24.211.238/upload/articlePictures/57c94f5cc2300.png","http://120.24.211.238/upload/articlePictures/57c94f6eea130.png","http://120.24.211.238/upload/articlePictures/57c94f7fdf2bf.png"]},{"article_id":"1237","classify_id":"61","user_id":"0","article_summary":"深扒一下抱抱堂的那些事情","article_html":"深扒一下抱抱堂的那些事情抱抱堂是谁？其实就是一个号称拥有互联网思维、用互联网打法经营的爆米花公司嘛，再实际一点，就如比友在深圳展会上反馈回来的消息来说，这就是一卖爆米花的公司嘛。。但抱抱堂的野心绝不是这么小。在投资界遇冷的时候，他们居然拿到了5000万的A轮投资！让我们来看看他到底有什么玩法：首先，","article_comment_number":"4","article_thumbs":"5","look_time":1779,"userthunm":"0","article":["http://120.24.211.238/upload/articlePictures/57733ea267624.jpg","http://120.24.211.238/upload/articlePictures/57733eaacc42e.png","http://120.24.211.238/upload/articlePictures/57733eb39f23d.jpg"]},{"article_id":"2039","classify_id":"61","user_id":"2809","article_summary":"猪蹄、小丸子、重庆小面，现在最火的单品，你应该看看这三个品牌","article_html":"猪蹄、小丸子、重庆小面，现在最火的单品，你应该看看这三个品牌有句话叫少即是多，如果你开的店没有一个能让消费者流连忘返的单品，那么，你的生意也许很快就会碰到问题。单品经营，在一定程度上缩小了消费群体，但同时也精化了消费群体。 在品牌传播的过程中，除了单品本身具备的高认知度能促进传播外，企业对单品的宣传","article_comment_number":"22","article_thumbs":"45","look_time":3594,"userthunm":"0","article":["http://app.bimuwang.com/upload/articlePictures/1479287127art2809.jpg","http://app.bimuwang.com/upload/articlePictures/1479287136art2809.jpg","http://app.bimuwang.com/upload/articlePictures/1479287145art2809.jpg"]}]
     * Recommend : [{"pid":"51","classify_name":"日餐"},{"pid":"60","classify_name":"西餐"},{"pid":"59","classify_name":"中式快餐"}]
     */

    private int exe_success;
    @JsonProperty(value="Recommend")
    private List<RecommendBean> Recommend;
    private String total;
    private DataBean data;
    private List<ArticledataBean> articledata;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<ArticledataBean> getArticledata() {
        return articledata;
    }

    public void setArticledata(List<ArticledataBean> articledata) {
        this.articledata = articledata;
    }

    public List<RecommendBean> getRecommend() {
        return Recommend;
    }

    public void setRecommend(List<RecommendBean> Recommend) {
        this.Recommend = Recommend;
    }


    public static class DataBean {

        /**
         * classify_id : 61
         * classify_name : 街头小吃
         * classify_icon : http://v30.bimuwang.com/upload/classifyIcon/5874cfa8370fe.jpg
         * fans : 21504
         */



        private String classify_id;
        private String classify_name;
        private String classify_icon;
        private String fans;

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

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }
    }

    public static class ArticledataBean {
        /**
         * article_id : 2365
         * classify_id : 61
         * user_id : 552
         * article_summary : 麦当劳的那么大鸡排，也敢和这几家鸡排专门店相比？
         * article_html : 当麦当劳开始卖力地推广它的“那么大”鸡排的时候，想做街头小吃鸡排的比友们是否有一种来者不善的感觉？实际上，比友们大可不必为此操心。麦当劳做的是正餐（让你午餐晚餐光吃一份鸡排，你一定接受不了吧？），鸡排我们更加喜欢将其放到街头小吃的范畴。也就是说，麦当劳进入鸡排领域，除了证实了国人对鸡排的接受程度很高
         * article_comment_number : 4
         * article_thumbs : 4
         * look_time : 10w+
         * userthunm : 0
         * article : ["http://30.bimuwang.com/upload/articlePictures/58aa990dbb4c0.jpeg","http://30.bimuwang.com/upload/articlePictures/58aa9918eb34d.jpeg","http://30.bimuwang.com/upload/articlePictures/58a510b61d762.jpg","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img0.png","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img1.png","http://30.bimuwang.com/upload/articlePictures/58a510beeb509.jpg","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img0.png","http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-85-img1.png","http://30.bimuwang.com/upload/articlePictures/58aa94c044c07.jpeg"]
         */

        private String article_id;
        private String classify_id;
        private String user_id;
        private String article_summary;
        private String article_html;
        private String article_comment_number;
        private String article_thumbs;
        private String look_time;
        private String userthunm;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private List<String> article;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(String classify_id) {
            this.classify_id = classify_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getArticle_summary() {
            return article_summary;
        }

        public void setArticle_summary(String article_summary) {
            this.article_summary = article_summary;
        }

        public String getArticle_html() {
            return article_html;
        }

        public void setArticle_html(String article_html) {
            this.article_html = article_html;
        }

        public String getArticle_comment_number() {
            return article_comment_number;
        }

        public void setArticle_comment_number(String article_comment_number) {
            this.article_comment_number = article_comment_number;
        }

        public String getArticle_thumbs() {
            return article_thumbs;
        }

        public void setArticle_thumbs(String article_thumbs) {
            this.article_thumbs = article_thumbs;
        }

        public String getLook_time() {
            return look_time;
        }

        public void setLook_time(String look_time) {
            this.look_time = look_time;
        }

        public String getUserthunm() {
            return userthunm;
        }

        public void setUserthunm(String userthunm) {
            this.userthunm = userthunm;
        }

        public List<String> getArticle() {
            return article;
        }

        public void setArticle(List<String> article) {
            this.article = article;
        }
    }

    public static class RecommendBean {
        /**
         * pid : 51
         * classify_name : 日餐
         */

        private String pid;
        private String classify_name;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getClassify_name() {
            return classify_name;
        }

        public void setClassify_name(String classify_name) {
            this.classify_name = classify_name;
        }
    }
}
