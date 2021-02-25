package com.bslp_lab1.changeorg.beans;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;

@Entity
@Table(name = "subscribers")
@IdClass(SubscribersId.class)
public class Subscribers {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "petition_id", referencedColumnName = "id")
    private Petition petition;

    @Column(name = "is_subscriber_anon")
    private boolean anon;


    public void settingSubscriberObject(Petition petition, User user,
                                        boolean isSubscriberAnon){
        this.anon = isSubscriberAnon;
        this.setUser(user);
        this.setPetition(petition);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Petition getPetition() {
        return petition;
    }

    public void setPetition(Petition petition) {
        this.petition = petition;
    }

    public boolean getAnon() {
        return anon;
    }

    public void setAnon(boolean anon) {
        this.anon = anon;
    }
}