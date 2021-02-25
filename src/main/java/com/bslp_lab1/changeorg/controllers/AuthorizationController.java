package com.bslp_lab1.changeorg.controllers;

import com.bslp_lab1.changeorg.beans.Message;
import com.bslp_lab1.changeorg.beans.User;

import com.bslp_lab1.changeorg.service.UserRepositoryService;
import com.bslp_lab1.changeorg.utils.JWTutils;
import com.bslp_lab1.changeorg.utils.TokenObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class AuthorizationController {

    @Autowired
    private JWTutils jwTutils;
    @Autowired
    private  UserRepositoryService userRepositoryService;
    @Autowired
    private Message message;


    @PutMapping("/register")
    public ResponseEntity<Message> register(@RequestBody User user) {
        if(this.userRepositoryService.save(user)){
            this.message.setAnswer("You are successful registered");
            return new ResponseEntity<Message>(this.message, HttpStatus.CREATED);
        }
        this.message.setAnswer("User with this email is already exists");
        return new ResponseEntity<Message>(this.message, HttpStatus.CONFLICT);
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenObject> auth(@RequestBody User user){
        User checkUser = userRepositoryService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(checkUser != null){
            TokenObject token = new TokenObject(jwTutils.generateToken(checkUser.getEmail()));
            return new ResponseEntity<TokenObject>(token, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<TokenObject>(HttpStatus.NOT_ACCEPTABLE);
    }
}
