package com.bslp_lab1.changeorg.beans;



import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.DTO.SubscribersDTO;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Table(name = "subscribers")
@IdClass(SubscribersIds.class)
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
    private Boolean anon;


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

    public Boolean getAnon() {
        return anon;
    }

    public void setAnon(Boolean anon) {
        this.anon = anon;
    }
}