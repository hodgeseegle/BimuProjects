package com.can.bimuprojects.Constant;

/**
 * 程序里用的一些常量
 * Created by can on 2017/4/12.
 */
public class AppConstant {

    //游客模式进行登录的请求值
    public static final String VISITOR_REQUEST = "visitor_request";

    //游客模式进行登录的请求值
    public static final String VISITOR_RESULT = "visitor_result";

    //返回值
    public static final int RESUTL = 0x11111;



    //保存用户id
    public static final String UID = "user_id";

    //保存用户名
    public static final String USER_NAME = "user_name";

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
