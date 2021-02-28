package com.bslp_lab1.changeorg.validation;

import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.exceptions.PetitionSignGoalValidationException;
import com.bslp_lab1.changeorg.exceptions.PetitionTopicValidationException;
import org.springframework.stereotype.Service;

@Service
public class PetitionValidationService {

    public void validatePetitionDTO(PetitionDTO petitionDTO) throws PetitionSignGoalValidationException, PetitionTopicValidationException {

        if(!validateTopicPetitionDTO(petitionDTO.getTopic())){
            throw new PetitionTopicValidationException();
        }else if(!validateSignGoalPetitionDTO(petitionDTO.getSignGoal())){
            throw new PetitionSignGoalValidationException();
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
