package com.can.bimuprojects.Module.Response;

/**
 * Created by can on 2017/4/27.
 * 活动页
 */

public class GetExerciseResponse {


    /**
     * exe_success : 1
     * title : wowo上线测试
     * content_url : <img src="http://v30.bimuwang.com/upload/activityPictures/58fb1c227eecc.jpg" alt="bimu" width="100%"  />
     * brand_id : 2094
     * brand_name : wowwoo熊港式小食
     * brand_img_url : http://v30.bimuwang.com/upload/brandLogo/585a31e9cb254.png
     * sign_up_num : 14
     * start_time : 1492852098
     * end_time : 1492790400
     * out_of_time : 1
     * has_follow : 1
     * true_name : 许
     */

    private int exe_success;
    private String title;
    private String content_url;
    private String brand_id;
    private String brand_name;
    private String brand_img_url;
    private String sign_up_num;
    private String start_time;
    private String end_time;
    private int out_of_time;
    private int has_follow;
    private String true_name;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

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

    public String getBrand_img_url() {
        return brand_img_url;
    }

    public void setBrand_img_url(String brand_img_url) {
        this.brand_img_url = brand_img_url;
    }

    public String getSign_up_num() {
        return sign_up_num;
    }

    public void setSign_up_num(String sign_up_num) {
        this.sign_up_num = sign_up_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getOut_of_time() {
        return out_of_time;
    }

    public void setOut_of_time(int out_of_time) {
        this.out_of_time = out_of_time;
    }

    public int getHas_follow() {
        return has_follow;
    }

    public void setHas_follow(int has_follow) {
        this.has_follow = has_follow;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }
}
