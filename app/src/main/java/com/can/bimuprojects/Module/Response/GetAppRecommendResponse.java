package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.AppRecommendBean;

import java.util.List;

/**
 * Created by can on 2017/5/16.
 * 应用推荐
 */

public class GetAppRecommendResponse {

    /**
     * exe_success : 1
     * list : [{"app_name":"程","app_logo":"http://v30.bimuwang.com/upload/brandLogo/591a757837687.jpg","app_link":"http://blog.csdn.net/evan_leung/article/details/51933313","app_about":"哈哈的哈沙发的和拉萨疯狂拉升克里夫哈撒艰苦的发哈市的发"},{"app_name":"测试的发送的发放","app_logo":"http://v30.bimuwang.com/upload/brandLogo/591a7649e2d94.jpg","app_link":"http://jui.org/#w_validation","app_about":"撒发生纠纷发哈市大酒店附近啊首发三大"},{"app_name":"敬礼","app_logo":"http://v30.bimuwang.com/upload/brandLogo/591a73ca53cea.jpg","app_link":"http://jui.org","app_about":"dsfasfdas"}]
     */

    private int exe_success;
    private List<AppRecommendBean> list;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<AppRecommendBean> getList() {
        return list;
    }

    public void setList(List<AppRecommendBean> list) {
        this.list = list;
    }

    public static class ListBean {
    }
}
