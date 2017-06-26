package com.can.bimuprojects.Module.Entity;

/**
 * Created by can on 2017/4/12.
 */
public class SearchResultBean {
    private int id; //品牌id
    private String image; //logo
    private String name;  //品牌name
    private String product;  //品牌产品
    private String money;  //投资金额
    private int followed; //表示是否已关注，0表示为关注，1表示已经关注
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }
}
