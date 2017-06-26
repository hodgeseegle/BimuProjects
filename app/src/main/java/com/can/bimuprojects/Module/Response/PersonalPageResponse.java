package com.can.bimuprojects.Module.Response;

import com.can.bimuprojects.Module.Entity.PersonalCardBean;

import java.util.List;

/**
 * Created by can on 2017/4/21.
 */
public class PersonalPageResponse {
    private String exe_success;

    private String user_image;

    private String user_name;

    private String has_followed;

    private String fans_count;

    private String personal_sign;

    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private List<PersonalCardBean> cards ;

    public PersonalPageResponse() {
    }

    public PersonalPageResponse(String exe_success) {
        this.exe_success = exe_success;
    }

    public String getExe_success() {
        return exe_success;
    }

    public void setExe_success(String exe_success) {
        this.exe_success = exe_success;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHas_followed() {
        return has_followed;
    }

    public void setHas_followed(String has_followed) {
        this.has_followed = has_followed;
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    public String getPersonal_sign() {
        return personal_sign;
    }

    public void setPersonal_sign(String personal_sign) {
        this.personal_sign = personal_sign;
    }

    public List<PersonalCardBean> getCards() {
        return cards;
    }

    public void setCards(List<PersonalCardBean> cards) {
        this.cards = cards;
    }
}
