package com.bslp_lab1.changeorg.configuration;


import com.bslp_lab1.changeorg.beans.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ChangeorgConfiguration {


    @Bean
    @Scope(scopeName = "prototype")
    public User getUser(){
        return new User();
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Petition getPetition(){
        return new Petition();
    }


    @Bean
    @Scope(scopeName = "prototype")
    public Subscribers getSubscribers(){
        return new Subscribers();
    }

    @Bean
    @Scope(scopeName = "prototype")
    public Message getMessage(){
        return new Message();
    }

    @Bean(name = "UserWrapper")
    @Scope(scopeName = "prototype")
    public UserWrapper getUserWrapper(){
        return new UserWrapper();
    }
}