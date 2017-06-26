package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/12.
 */
public class GetVerifyCodeRequest {
    String phone_num;

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public GetVerifyCodeRequest(String phone_num) {
        this.phone_num = phone_num;
    }
}
