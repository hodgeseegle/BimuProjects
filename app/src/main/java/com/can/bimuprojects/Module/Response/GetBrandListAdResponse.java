package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.BrandListAdBean;

import java.util.List;

/**
 * Created by can on 17/4/28.
 */
public class GetBrandListAdResponse {
    private int exe_success;
    private List<BrandListAdBean> cards;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<BrandListAdBean> getCards() {
        return cards;
    }

    public void setCards(List<BrandListAdBean> cards) {
        this.cards = cards;
    }
}
