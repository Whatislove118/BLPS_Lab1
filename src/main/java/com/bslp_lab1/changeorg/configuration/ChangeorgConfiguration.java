package com.bslp_lab1.changeorg.configuration;


import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.DTO.SubscribersDTO;
import com.bslp_lab1.changeorg.DTO.UserDTO;
import com.bslp_lab1.changeorg.beans.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableWebMvc
public class ChangeorgConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    @Scope(scopeName = "prototype")
    public User getUser(){ return new User(); }

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
    public ResponseMessageDTO getResponseMessageDTO(){
        return new ResponseMessageDTO();
    }

    @Bean(name = "UserDTO")
    @Scope(scopeName = "prototype")
    public UserDTO getUserDTO(){
        return new UserDTO();
    }

    @Bean(name = "PetitionDTO")
    @Scope(scopeName = "prototype")
    public PetitionDTO getPetitionDTO() {return new PetitionDTO();}

    @Bean(name = "SubscribersDTO")
    @Scope(scopeName = "prototype")
    public SubscribersDTO getSubscribersDTO() {return new SubscribersDTO();}






}