package com.bslp_lab1.changeorg.DTO;

import java.io.Serializable;

public class PetitionDTO implements Serializable, MessageDTO {
    private Long ID;
    private String topic;
    private UserDTO owner;
    private String imageSrc;
    private String description;
    private Integer countSign;
    private Integer signGoal;


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCountSign() {
        return countSign;
    }

    public void setCountSign(Integer countSign) {
        this.countSign = countSign;
    }

    public Integer getSignGoal() {
        return signGoal;
    }

    public void setSignGoal(Integer signGoal) {
        this.signGoal = signGoal;
    }
}
