package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.User;
import com.bslp_lab1.changeorg.repository.UserRepository;
import com.bslp_lab1.changeorg.utils.JWTutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserRepositoryService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTutils jwTutils;


    public UserRepository getUserRepService() {
        return userRepository;
    }

    public User getUserFromRequest(HttpServletRequest request){
        String token = jwTutils.getTokenFromRequest(request);
        String email = jwTutils.getEmailFromToken(token);
        User user =  this.findByEmail(email);
        if(user != null) {
            return user;
        }
        return null;
    }

    public boolean save(User user){
        try{
            this.userRepository.save(user);
            return true;
        }catch (Exception e){
            System.out.println("User " + user.getEmail() + " already exists");
            return false;
        }
    }

    public User findById(Long id){
        try{
            return this.userRepository.findByID(id);
        }catch (Exception e){
            System.out.println("User not found");
            return null;
        }
    }


    public User findByEmailAndPassword(String email, String password){
        try{
           return this.userRepository.findByEmailAndPassword(email, password);
        }catch (Exception e){
            System.out.println("User " + email + " not found");
            return null;
        }
    }

    public User findByEmail(String email){
        try{
            return this.userRepository.findByEmail(email);
        }catch (Exception e){
            System.out.println("User " + email + " not in database");
            return null;
        }
    }
}
