package com.bslp_lab1.changeorg.DTO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String surname;
    private String name;
    private Long Id;
    private String password;
    private String email;


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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
