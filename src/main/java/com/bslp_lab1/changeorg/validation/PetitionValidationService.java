package com.bslp_lab1.changeorg.validation;

import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.exceptions.PetitionValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PetitionValidationService {

    public void validatePetitionDTO(PetitionDTO petitionDTO) throws PetitionValidationException {
        if(!validateTopicPetitionDTO(petitionDTO.getTopic())){
            throw new PetitionValidationException("Invalid topic", HttpStatus.BAD_REQUEST);
        }else if(!validateSignGoalPetitionDTO(petitionDTO.getSignGoal())){
            throw new PetitionValidationException("Sign goal must be a positive int", HttpStatus.BAD_REQUEST);
        }

    }

    public boolean validateTopicPetitionDTO(String topic){
        int length = topic.length();
        if(length < 5){
            return false;
        }else if(length > 40){
            return false;
        }
        return true;
    }

    public boolean validateSignGoalPetitionDTO(int signGoal){
        if(signGoal<0){
            return false;
        }
        return true;
    }



}
