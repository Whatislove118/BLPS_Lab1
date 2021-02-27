package com.bslp_lab1.changeorg.DTO;


import java.io.Serializable;

public class SubscribersDTO implements Serializable {
    private UserDTO userDTO;
    private PetitionDTO petition;
    private Boolean anon;



    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO user) {
        this.userDTO = user;
    }

    public PetitionDTO getPetition() {
        return petition;
    }

    public void setPetition(PetitionDTO petition) {
        this.petition = petition;
    }

    public Boolean getAnon() {
        return anon;
    }

    public void setAnon(Boolean sSubscriberAnon) {
        this.anon = sSubscriberAnon;
    }
}
