package com.bslp_lab1.changeorg.controllers;


import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.DTO.SubscribersDTO;
import com.bslp_lab1.changeorg.DTO.UserDTO;
import com.bslp_lab1.changeorg.beans.*;
import com.bslp_lab1.changeorg.service.DTOConverter;
import com.bslp_lab1.changeorg.service.PetitionRepositoryService;
import com.bslp_lab1.changeorg.service.SubscribersRepositoryService;
import com.bslp_lab1.changeorg.service.UserRepositoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/petition")
@Api(value = "Petition api")
/* TODO
    закончить внедрение DTO
*   переделать логику контроллеров
    внедрить модуль валидации
*/
public class PetitionManageController {


    @Autowired
    private PetitionRepositoryService petitionRepositoryService;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private SubscribersRepositoryService subscribersRepositoryService;
    @Autowired
    private DTOConverter dtoConverter;

    @Autowired
    private Subscribers subscribers;
    @Autowired
    private ResponseMessageDTO message;
    @Autowired
    private Petition petition;


    @Autowired
    private PetitionDTO petitionDTO;
    @Autowired
    private User user;
//    @Autowired
//    private UserDTO userDTO;



    @PutMapping("/add")
    @ApiOperation(value = "add petition")
    public ResponseEntity<ResponseMessageDTO> addPetition(@RequestBody PetitionDTO petitionDTO, HttpServletRequest request){
        try {
            User owner = this.userRepositoryService.getUserFromRequest(request);
            petition = dtoConverter.convertPetitionFromDTO(petitionDTO, owner);
            this.petitionRepositoryService.save(petition);
            this.message.setAnswer("Petition was added");
            return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.CREATED);
        }catch (NullPointerException e){
            this.message.setAnswer("Petition without owner. Please, try later");
            return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }catch (DataIntegrityViolationException e){
            this.message.setAnswer("Can't save petition. Petition with same topic already exists");
            return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public ArrayList<PetitionDTO> getAllPetitions(){
        ArrayList<PetitionDTO> petitionDTOS = new ArrayList<>();
        ArrayList<Petition> petitions = new ArrayList<>(this.petitionRepositoryService.findAll());
        for (Petition petition : petitions){
            petitionDTOS.add(dtoConverter.convertPetitionToDTO(petition));
        }
        return petitionDTOS;
    }

    @PutMapping("/{id}/subscribe")
    public ResponseEntity getSubscribe(@RequestBody SubscribersDTO petitionInfo, @PathVariable("id") long id, HttpServletRequest request){
        try{
            User subscriber = this.userRepositoryService.getUserFromRequest(request);
            System.out.println(subscriber.getName());
            try{
                petition = this.petitionRepositoryService.findById(id);
            }catch (NullPointerException e){
                this.message.setAnswer("Petition not found. Please, try later");
                return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.NOT_FOUND);
            }
            petition.incrementCountSign();
            this.petitionRepositoryService.save(petition);
            subscribers.settingSubscriberObject(petition, subscriber, petitionInfo.getAnon());
            System.out.println(subscribers.getPetition().getID());
            try {
                this.subscribersRepositoryService.save(subscribers);
                this.message.setAnswer("Subscribed");
                return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.CREATED);
            }catch (DataIntegrityViolationException e){
                this.message.setAnswer("You are already subscribed");
                return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.CONFLICT);
            }

        }catch (NullPointerException e){
            this.message.setAnswer("User not found. Please, try again");
            return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPetitionById(@PathVariable("id") Long id){
        try {
            Petition petition = this.petitionRepositoryService.findById(id);
            petitionDTO = dtoConverter.convertPetitionToDTO(petition);
            return new ResponseEntity<PetitionDTO>(petitionDTO, HttpStatus.OK);
        }catch (NoSuchElementException e){
            message.setAnswer("Petition not found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity getAllSubscribedUser(@PathVariable("id") long id){
        try {
            ArrayList<Subscribers> subscribers = this.subscribersRepositoryService.findAllByPetitionId(id);
            ArrayList<UserDTO> users = new ArrayList<>();
            for(Subscribers sub : subscribers){
                if(sub.getAnon()){
                    UserDTO userDTO = dtoConverter.convertAnonUserToDTO();
                    userDTO.settingWrapperUserAnon();
                    users.add(userDTO);
                }else{
                    try{
                        user = this.userRepositoryService.findById(sub.getUser().getID());
                    }catch (NullPointerException e){
                        message.setAnswer("User not found");
                        return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
                    }
                    UserDTO userDTO = dtoConverter.convertUserToDTO(user);
                    userDTO.settingWrapperUser(sub.getUser().getSurname(), sub.getUser().getName(), sub.getUser().getID());
                    users.add(userDTO);
                    }
                }
            return new ResponseEntity<ArrayList<UserDTO>>(users, HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            message.setAnswer("You are already subscribed");
            return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.BAD_REQUEST);
        }

    }
}
