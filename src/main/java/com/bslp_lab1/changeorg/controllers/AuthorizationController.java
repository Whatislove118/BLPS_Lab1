package com.bslp_lab1.changeorg.controllers;

import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.DTO.UserDTO;
import com.bslp_lab1.changeorg.beans.User;

import com.bslp_lab1.changeorg.service.DTOConverter;
import com.bslp_lab1.changeorg.service.UserRepositoryService;
import com.bslp_lab1.changeorg.utils.JWTutils;
import com.bslp_lab1.changeorg.DTO.TokenObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class AuthorizationController {

    @Autowired
    private JWTutils jwTutils;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private  UserRepositoryService userRepositoryService;
    @Autowired
    private ResponseMessageDTO message;
    @Autowired
    private User user;


    @PutMapping("/register")
    public ResponseEntity<ResponseMessageDTO> register(@RequestBody UserDTO userDTO) {
        user = dtoConverter.convertUserFromDTO(userDTO);
        try {
            this.userRepositoryService.save(user);
        }catch (DataIntegrityViolationException e){
            this.message.setAnswer("User with this email is already exists");
            return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.CONFLICT);
        }
        this.message.setAnswer("You successful registered");
        return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.ACCEPTED);
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserDTO userDTO){
        user = dtoConverter.convertUserFromDTO(userDTO);
        try {
            user = userRepositoryService.findByEmailAndPassword(user.getEmail(), user.getPassword());
            TokenObject token = new TokenObject(jwTutils.generateToken(user.getEmail()));
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        }catch (NullPointerException e){
            message.setAnswer("User not found! Please, repeat authorization");
            return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
