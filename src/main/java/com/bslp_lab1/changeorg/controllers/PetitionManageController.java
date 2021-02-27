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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


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



    @PutMapping("/add")
    @ApiOperation(value = "add petition")
    public ResponseEntity<ResponseMessageDTO> addPetition(@RequestBody PetitionDTO petitionDTO, HttpServletRequest request){
        petition = dtoConverter.convertPetitionFromDTO(petitionDTO);
        User owner = this.userRepositoryService.getUserFromRequest(request);
        if(owner != null){
            petition.setOwner(owner);
        }else{
            this.message.setAnswer("Petition without owner. Internal server error");
            return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.BAD_REQUEST);
        }
        if(this.petitionRepositoryService.save(petition)){
            this.message.setAnswer("Petition was added");
            return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.CREATED);
        }
        this.message.setAnswer("Can't save petition. Petition with same topic already exists");
        return new ResponseEntity<ResponseMessageDTO>(this.message, HttpStatus.CONFLICT);
    }

    @GetMapping("/all")
    public Iterable<Petition> getAllPetitions(){
        return this.petitionRepositoryService.findAll();
    }

    @PutMapping("/{id}/subscribe")
    public ResponseEntity<ResponseMessageDTO> getSubscribe(@RequestBody SubscribersDTO petitionInfo,
                                                           @PathVariable("id") long id, HttpServletRequest request){

        User subscriber = this.userRepositoryService.getUserFromRequest(request);
        if(subscriber == null){
            this.message.setAnswer("User not found. Your token invalid. Access denied");
            return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.FORBIDDEN);
        }
        Petition petition = this.petitionRepositoryService.findById(id);
        if(petition == null){
            this.message.setAnswer("Petition not found. ");
            return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.NOT_FOUND);
        }
        petition.incrementCountSign();
        System.out.println(petitionInfo.getAnon());
        this.petitionRepositoryService.save(petition);
        subscribers.settingSubscriberObject(petition, subscriber, petitionInfo.getAnon());
        if(this.subscribersRepositoryService.save(subscribers)){
            this.message.setAnswer("Subscribed");
            return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.CREATED);
        }
        this.message.setAnswer("You are already subscribed");
        return new ResponseEntity<ResponseMessageDTO>(message, HttpStatus.CONFLICT);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PetitionDTO> getPetitionById(@PathVariable("id") Long id){
        Petition petition = this.petitionRepositoryService.findById(id);
        petitionDTO = dtoConverter.convertPetitionToDTO(petition);
        if(petition != null){
            return new ResponseEntity<PetitionDTO>(petitionDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<PetitionDTO>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}/users")
    public ResponseEntity<ArrayList<UserDTO>> getAllSubscribedUser(@PathVariable("id") long id){
        ArrayList<Subscribers> subscribers = this.subscribersRepositoryService.findAllByPetitionId(id);
        if(subscribers == null){
            return new ResponseEntity<ArrayList<UserDTO>>(HttpStatus.NOT_FOUND);
        }
        ArrayList<UserDTO> users = new ArrayList<>();
        for(Subscribers sub : subscribers){
            if(sub.getAnon()){
                UserDTO anonUserWrapper = dtoConverter.convertAnonUserToDTO();
                anonUserWrapper.settingWrapperUserAnon();
                users.add(anonUserWrapper);

            }else{
                User user = this.userRepositoryService.findById(sub.getUser().getID());
                if (user == null){
                    continue;
                }else{
                    UserDTO userWrapper = dtoConverter.convertUserToDTO(user);
                    userWrapper.settingWrapperUser(sub.getUser().getSurname(), sub.getUser().getName(), sub.getUser().getID());
                    users.add(userWrapper);
                }
            }
        }
        return new ResponseEntity<ArrayList<UserDTO>>(users, HttpStatus.OK);
    }
}
