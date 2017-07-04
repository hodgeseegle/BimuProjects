package com.can.bimuprojects.Module.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/17.
 * 图库
 */

public class BrandImgResponse {


    /**
     * exe_success : 1
     * total : 18
     * data : [{"tag":"综合","list":["http://v30.bimuwang.com/upload/brandLogo/59521bf33e573.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3692a2.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3810ea.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3993dd.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3c15c9.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dced5af4.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcf0e314.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcf2ebe3.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcfd4f86.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dd09fbf8.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e0a39925.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e0ee4c02.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e4860e69.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e487d2ec.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48a08d3.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48b9eaa.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48d5f75.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e6e529f9.jpg"]},{"tag":"产品设计","list":["http://v30.bimuwang.com/upload/brandLogo/59521bf33e573.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3692a2.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3810ea.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3993dd.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3c15c9.jpg"]},{"tag":"笨蛋文能","list":["http://v30.bimuwang.com/upload/brandLogo/59521dced5af4.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcf0e314.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcf2ebe3.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcfd4f86.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dd09fbf8.jpg"]},{"tag":"文能豆逼","list":["http://v30.bimuwang.com/upload/brandLogo/59521e0a39925.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e0ee4c02.jpg"]},{"tag":"蟠桃","list":["http://v30.bimuwang.com/upload/brandLogo/59521e4860e69.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e487d2ec.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48a08d3.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48b9eaa.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48d5f75.jpg"]},{"tag":"比目","list":["http://v30.bimuwang.com/upload/brandLogo/59521e6e529f9.jpg"]}]
     */

    private int exe_success;
    private int total;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * tag : 综合
         * list : ["http://v30.bimuwang.com/upload/brandLogo/59521bf33e573.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3692a2.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3810ea.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3993dd.jpg","http://v30.bimuwang.com/upload/brandLogo/59521bf3c15c9.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dced5af4.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcf0e314.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcf2ebe3.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dcfd4f86.jpg","http://v30.bimuwang.com/upload/brandLogo/59521dd09fbf8.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e0a39925.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e0ee4c02.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e4860e69.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e487d2ec.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48a08d3.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48b9eaa.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e48d5f75.jpg","http://v30.bimuwang.com/upload/brandLogo/59521e6e529f9.jpg"]
         */

        private String tag;
        private ArrayList<String> list;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public ArrayList<String> getList() {
            return list;
        }

        public void setList(ArrayList<String> list) {
            this.list = list;
        }
    }
}
