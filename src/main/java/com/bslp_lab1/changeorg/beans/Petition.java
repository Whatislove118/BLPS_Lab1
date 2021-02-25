package com.bslp_lab1.changeorg.beans;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "change_org_petition", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"topic"})
})
public class Petition {
    @Id
    @GeneratedValue
    private long ID;
    @NotNull
    private String topic;

    @NotNull
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "change_org_users_id")
    @OrderColumn(name = "change_org_petition_owner")
    private User owner;


    @NotNull
    private String imageSrc;
    @NotNull
    private String description;
    @NotNull
    @ColumnDefault("0")
    private int countSign;
    @NotNull
    private int signGoal;



    public void incrementCountSign(){
        this.setCountSign(this.getCountSign() + 1);
    }
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
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

    public int getCountSign() {
        return countSign;
    }

    public void setCountSign(int countSign) {
        this.countSign = countSign;
    }

    public int getSignGoal() {
        return signGoal;
    }

    public void setSignGoal(int signGoal) {
        this.signGoal = signGoal;
    }

}
