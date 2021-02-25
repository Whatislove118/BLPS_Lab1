package com.bslp_lab1.changeorg.beans;


import java.io.Serializable;

public class SubscribersId implements Serializable {
    private long user;
    private long petition;
    private boolean anon;



    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getPetition() {
        return petition;
    }

    public void setPetition(long petition) {
        this.petition = petition;
    }

    public boolean getAnon() {
        return anon;
    }

    public void setAnon(boolean sSubscriberAnon) {
        this.anon = sSubscriberAnon;
    }
}
