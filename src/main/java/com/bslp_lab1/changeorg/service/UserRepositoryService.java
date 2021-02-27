package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.User;
import com.bslp_lab1.changeorg.repository.UserRepository;
import com.bslp_lab1.changeorg.utils.JWTutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
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

    public User getUserFromRequest(HttpServletRequest request) throws NullPointerException{
        String token = jwTutils.getTokenFromRequest(request);
        String email = jwTutils.getEmailFromToken(token);
        User user =  this.findByEmail(email);
        System.out.println("getting user from request" + user.getEmail());
        return user;
    }

    public void save(User user) throws DataIntegrityViolationException {
        this.userRepository.save(user);
    }

    public User findById(Long id) throws NullPointerException{
        return this.userRepository.findByID(id);
    }


    public User findByEmailAndPassword(String email, String password){
        return this.userRepository.findByEmailAndPassword(email, password);
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
