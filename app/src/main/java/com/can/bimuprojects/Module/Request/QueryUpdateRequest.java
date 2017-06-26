package com.can.bimuprojects.Module.Request;

/**
 * Created by can on 17/4/11.
 */
public class QueryUpdateRequest {
    int device; // 0 代表安卓设备

    public QueryUpdateRequest(int device) {
        this.device = device;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }
}
