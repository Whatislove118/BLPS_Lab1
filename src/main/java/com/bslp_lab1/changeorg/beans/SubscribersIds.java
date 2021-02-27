package com.bslp_lab1.changeorg.beans;

import java.io.Serializable;

public class SubscribersIds implements Serializable {

    private User user;
    private Boolean anon;
    private Petition petition;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAnon() {
        return anon;
    }

    public void setAnon(Boolean anon) {
        this.anon = anon;
    }

    public Petition getPetition() {
        return petition;
    }

    public void setPetition(Petition petition) {
        this.petition = petition;
    }
}
