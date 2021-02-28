package com.bslp_lab1.changeorg.validation;

import com.bslp_lab1.changeorg.DTO.UserDTO;

import com.bslp_lab1.changeorg.exceptions.UserEmailValidationException;
import com.bslp_lab1.changeorg.exceptions.UserNameValidationException;
import com.bslp_lab1.changeorg.exceptions.UserPasswordValidationException;
import com.bslp_lab1.changeorg.exceptions.UserSurnameValidationException;
import org.springframework.stereotype.Service;

@Service
public class ValidationUserService {

    public void validateUserDTO(UserDTO userDTO) throws UserEmailValidationException, UserSurnameValidationException,
            UserNameValidationException, UserPasswordValidationException {

        if(!validatePasswordUserDTO(userDTO.getPassword())){
            throw new UserPasswordValidationException();
        }else if(!validateEmailUserDTO(userDTO.getEmail())){
            throw new UserEmailValidationException();
        }else if(!validateSurnameUserDTO(userDTO.getSurname())){
            throw new UserSurnameValidationException();
        }else if(!validateNameUserDTO(userDTO.getName())){
            throw new UserNameValidationException();
        }

    }

    private boolean validatePasswordUserDTO(String password){
        int length = password.length();
        if(length == 0){
            return false;
        }else if(length>20){
            return false;
        }
        return true;
    }

    private boolean validateNameUserDTO(String name){
        if (checkIntString(name)){
            return false;
        }else if(name.length() < 3){
            return false;
        }
        return true;
    }

    private boolean validateSurnameUserDTO(String surname){
        if (checkIntString(surname)){
            return false;
        }else if(surname.length() < 3){
            return false;
        }
        return true;
    }

    private boolean validateEmailUserDTO(String email){
        if(!email.contains("@")){
            return false;
        }else if(email.length() < 4 || email.length() > 20){
            return false;
        }
        return true;

    }

    private static boolean checkIntString(String sequence){
        char[] chars = sequence.toCharArray();

        for (char ch : chars) {
            if (Character.isDigit(ch)) {
                return true;
            }
        }
        return false;

    }
}


