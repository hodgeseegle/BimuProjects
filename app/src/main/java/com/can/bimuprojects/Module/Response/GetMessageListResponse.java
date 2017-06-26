package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.MessageBean;

import java.util.List;

/**
 * Created by pak2c on 16/4/28.
 * Email : 9532244366@qq.com
 */
public class GetMessageListResponse {
    private int exe_success;
    private List<MessageBean> cards;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public List<MessageBean> getCards() {
        return cards;
    }

    public void setCards(List<MessageBean> cards) {
        this.cards = cards;
    }
}
