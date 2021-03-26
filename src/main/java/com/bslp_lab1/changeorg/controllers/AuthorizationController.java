package com.bslp_lab1.changeorg.controllers;

import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.DTO.UserDTO;
import com.bslp_lab1.changeorg.exceptions.*;
import com.bslp_lab1.changeorg.service.UserRepositoryService;
import com.bslp_lab1.changeorg.validation.ValidationUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Api(value = "Authorization api")
/* TODO Разобраться с exception
*   переделать bpmn
*   вынести бизнес логику с контроллеров
* */
public class AuthorizationController {

    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private ValidationUserService validationUserService;




    @PutMapping("/register")
    public ResponseEntity<ResponseMessageDTO> register(@RequestBody UserDTO userDTO) {
        ResponseMessageDTO message = new ResponseMessageDTO();
        try{
            validationUserService.validateUserDTO(userDTO);
        }catch (UserValidationException e) {
            message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
        return userRepositoryService.registerUserDTO(userDTO);
    }

    @PostMapping("/auth")
    public ResponseEntity<ResponseMessageDTO> auth(@RequestBody UserDTO userDTO){
        ResponseMessageDTO message = new ResponseMessageDTO();
        try{
            validationUserService.validateUserDTO_FOR_AUTH(userDTO);
        }catch (UserValidationException e) {
            message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
        return userRepositoryService.authUserDTO(userDTO);
    }
}
