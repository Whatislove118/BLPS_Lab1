package com.bslp_lab1.changeorg.beans;

import java.io.Serializable;

public class SubscribersIds implements Serializable {

    private Long user;
    private Boolean anon;
    private Long petition;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Boolean getAnon() {
        return anon;
    }

    public void setAnon(Boolean anon) {
        this.anon = anon;
    }

    public Long getPetition() {
        return petition;
    }

    public void setPetition(Long petition) {
        this.petition = petition;
    }
}
