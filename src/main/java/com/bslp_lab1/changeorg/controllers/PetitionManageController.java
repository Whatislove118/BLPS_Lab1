package com.bslp_lab1.changeorg.controllers;


import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.DTO.SubscribersDTO;
import com.bslp_lab1.changeorg.beans.*;
import com.bslp_lab1.changeorg.exceptions.PetitionNotFoundException;
import com.bslp_lab1.changeorg.exceptions.PetitionValidationException;
import com.bslp_lab1.changeorg.exceptions.UserNotFoundException;
import com.bslp_lab1.changeorg.service.PetitionRepositoryService;
import com.bslp_lab1.changeorg.service.SubscribersRepositoryService;
import com.bslp_lab1.changeorg.service.UserRepositoryService;
import com.bslp_lab1.changeorg.validation.PetitionValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;



@RestController
@RequestMapping("/petition")
@Api(value = "Petition api")

public class PetitionManageController {


    @Autowired
    private PetitionRepositoryService petitionRepositoryService;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private SubscribersRepositoryService subscribersRepositoryService;

    private ResponseMessageDTO message;

    @Autowired
    private Petition petition;



    @Autowired
    private PetitionValidationService petitionValidationService;



    @PutMapping("/add")
    @ApiOperation(value = "add petition")
    public ResponseEntity<ResponseMessageDTO> addPetition(@RequestBody PetitionDTO petitionDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            petitionValidationService.validatePetitionDTO(petitionDTO);
        }catch (PetitionValidationException e){
            message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        return this.petitionRepositoryService.saveFromDTO(petitionDTO, request);
    }

    @GetMapping("/all")
    public ArrayList<PetitionDTO> getAllPetitions(){
        return this.petitionRepositoryService.getAllPetitions();

    }


    @PutMapping("/{id}/subscribe")
    public ResponseEntity getSubscribe(@RequestBody SubscribersDTO petitionInfo, @PathVariable("id") long id, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User subscriber = this.userRepositoryService.getUserFromRequest(request);
            try{
                petition = this.petitionRepositoryService.findById(id);
            }catch (PetitionNotFoundException e){
                this.message.setAnswer(e.getErrMessage());
                return new ResponseEntity<>(message, e.getErrStatus());
            }
            this.petitionRepositoryService.incrementCountSign(petition);
            return this.subscribersRepositoryService.subscribe(petition, subscriber, petitionInfo.getAnon());
        }catch (UserNotFoundException e){
            this.message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPetitionById(@PathVariable("id") Long id){
        message = new ResponseMessageDTO();
        try {
            PetitionDTO petitionDTO = this.petitionRepositoryService.findByIdToResponse(id);
            return new ResponseEntity<>(petitionDTO, HttpStatus.OK);
        }catch (PetitionNotFoundException e){
            message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity getAllSubscribedUser(@PathVariable("id") long id){
        message = new ResponseMessageDTO();
        try {
            return this.subscribersRepositoryService.getAllSubscribedUsers(id);
        }catch (UserNotFoundException e){
            message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }catch (PetitionNotFoundException e){
            ResponseMessageDTO message = new ResponseMessageDTO();
            message.setAnswer(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }
}
