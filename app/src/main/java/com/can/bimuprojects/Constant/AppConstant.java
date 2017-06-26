package com.can.bimuprojects.Constant;

/**
 * 程序里用的一些常量
 * Created by can on 2017/4/12.
 */
public class AppConstant {

    //保存用户id
    public static final String UID = "user_id";

    public static final String TMP_PATH = "clip_temp.jpg";

    public static final String RESULT_PATH = "crop_image";

    //列表类型常量，Intent传递
    /**
     * 使用intent的putStringExtra方法，作为key使用。
     */
    public static final String LIST_TYPE = "list_type";
    /**
     * 类型为消息列表
     */
    public static final String LIST_TYPE_MESSAGE="type_message";

    //退出登录
    public static final String QUIT_LOGIN = "quit_login";

    //登录弹窗跳转请求码
    public static final int LOGIN_REQUEST = 0x111;

}
