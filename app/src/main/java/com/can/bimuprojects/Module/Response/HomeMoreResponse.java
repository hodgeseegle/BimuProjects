package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.RecommandCardBean;

import java.util.List;

/**
 * Created by can on 2017/4/12.
 */
public class HomeMoreResponse {

    public String exe_success;
    public List<RecommandCardBean> cards;

    public HomeMoreResponse() {
    }

    public String getExe_success() {
        return exe_success;
    }

    public void setExe_success(String exe_success) {
        this.exe_success = exe_success;
    }

    public List<RecommandCardBean> getCards() {
        return cards;
    }

    public void setCards(List<RecommandCardBean> cards) {
        this.cards = cards;
    }
}
