package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.DTO.TokenObject;
import com.bslp_lab1.changeorg.DTO.UserDTO;
import com.bslp_lab1.changeorg.beans.User;

import com.bslp_lab1.changeorg.exceptions.UserNotFoundException;
import com.bslp_lab1.changeorg.repository.UserRepository;
import com.bslp_lab1.changeorg.utils.JWTutils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserRepositoryService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTutils jwTutils;
    @Autowired
    private User user;
    @Autowired
    private DTOConverter dtoConverter;

    Logger logger = LogManager.getLogger(UserRepositoryService.class);


    public UserRepository getUserRepService() {
        return userRepository;
    }

    public User getUserFromRequest(HttpServletRequest request) throws UserNotFoundException{
        String token = jwTutils.getTokenFromRequest(request);
        String email = jwTutils.getEmailFromToken(token);
        try {
            User user = this.findByEmail(email);
            logger.log(Level.INFO, "getting user from request" + user.getEmail());
            return user;
        }catch (UserNotFoundException e){
            throw e;
        }
    }

    public ResponseEntity<ResponseMessageDTO> registerUserDTO(UserDTO userDTO){
        ResponseMessageDTO message = new ResponseMessageDTO();
        user = dtoConverter.convertUserFromDTO(userDTO);
        String answerText = "";
        try{
            this.save(user);
            message.setAnswer("Вы были успешно зарегестрированы");
        }catch (DataIntegrityViolationException e){
            if(e.getCause().getClass() == ConstraintViolationException.class){
                answerText = "Пользователь с таким email уже существует";
            }else{
                answerText = "УПС! Произошла ошибка, пожалуйста, попробуйте позднее";
            }
            message.setAnswer(answerText);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    public ResponseEntity<ResponseMessageDTO> authUserDTO(UserDTO userDTO){
        ResponseMessageDTO message = new ResponseMessageDTO();
        try {
            user = this.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
            TokenObject token = new TokenObject(jwTutils.generateToken(user.getEmail()));
            message.setAnswer(token.getToken());
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        }catch (UserNotFoundException e){
            message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    public void save(User user)  {
        this.userRepository.save(user);
    }

    public User findById(Long id) throws UserNotFoundException{
        User user = this.userRepository.findByID(id);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким email не найден", HttpStatus.BAD_REQUEST);
        }
        return user;
    }


    public User findByEmailAndPassword(String email, String password) throws UserNotFoundException{
        User user  = this.userRepository.findByEmailAndPassword(email, password);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким email не найден", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    public User findByEmail(String email) throws UserNotFoundException{
        User user = this.userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким email не найден. Ошибка авторизации", HttpStatus.UNAUTHORIZED);
        }
        return user;

    }
}
