package com.bslp_lab1.changeorg.configuration;


import com.bslp_lab1.changeorg.beans.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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