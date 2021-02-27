package com.bslp_lab1.changeorg.beans;

import com.sun.istack.NotNull;
import io.swagger.models.auth.In;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "change_org_petition", uniqueConstraints =
        @UniqueConstraint(columnNames = "topic")
)
public class Petition {
    @Id
    @GeneratedValue
    private Long ID;
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
    private Integer countSign;
    @NotNull
    private Integer signGoal;



    public void incrementCountSign(){
        this.setCountSign(this.getCountSign() + 1);
    }
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
