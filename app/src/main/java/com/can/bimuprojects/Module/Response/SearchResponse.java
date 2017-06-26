package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.SearchResultBean;

import java.util.List;

/**
 * Created by can on 17/4/24.
 */
public class SearchResponse {
    private int exe_success;
    private List<SearchResultBean> cards;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<SearchResultBean> getCards() {
        return cards;
    }

    public void setCards(List<SearchResultBean> cards) {
        this.cards = cards;
    }
}
