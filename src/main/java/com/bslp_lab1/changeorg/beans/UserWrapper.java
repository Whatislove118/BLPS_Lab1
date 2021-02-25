package com.bslp_lab1.changeorg.beans;

public class UserWrapper {
    private String surname;
    private String name;
    private long Id;


    public void settingWrapperUser(String surname,
                                   String name,
                                   long id){
        this.name = name;
        this.surname = surname;
        this.Id = id;
    }

    public void settingWrapperUserAnon(){
        this.surname = "Анонимус";
        this.name = "Анон";
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
