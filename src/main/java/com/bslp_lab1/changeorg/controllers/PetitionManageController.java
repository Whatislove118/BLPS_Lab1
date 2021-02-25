package com.bslp_lab1.changeorg.controllers;


import com.bslp_lab1.changeorg.beans.*;
import com.bslp_lab1.changeorg.service.PetitionRepositoryService;
import com.bslp_lab1.changeorg.service.SubscribersRepositoryService;
import com.bslp_lab1.changeorg.service.UserRepositoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    private ApplicationContext applicationContext;
    @Autowired
    private PetitionRepositoryService petitionRepositoryService;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private SubscribersRepositoryService subscribersRepositoryService;
    @Autowired
    private Subscribers subscribers;
    @Autowired
    private Message message;



    private UserWrapper createUserWrapper(){
        return (UserWrapper) applicationContext.getBean("UserWrapper");
    }


    @PutMapping("/add")
    @ApiOperation(value = "add petition")
    public ResponseEntity<Message> addPetition(@RequestBody Petition petition, HttpServletRequest request){
        User owner = this.userRepositoryService.getUserFromRequest(request);
        if(owner != null){
            petition.setOwner(owner);
        }else{
            this.message.setAnswer("Petition without owner. Internal server error");
            return new ResponseEntity<Message>(this.message, HttpStatus.BAD_REQUEST);
        }
        if(this.petitionRepositoryService.save(petition)){
            this.message.setAnswer("Petition was added");
            return new ResponseEntity<Message>(this.message, HttpStatus.CREATED);
        }
        this.message.setAnswer("Can't save petition. Petition with same topic already exists");
        return new ResponseEntity<Message>(this.message, HttpStatus.CONFLICT);
    }

    @GetMapping("/all")
    public Iterable<Petition> getAllPetitions(){
        return this.petitionRepositoryService.findAll();
    }

    @PutMapping("/{id}/subscribe")
    public ResponseEntity<Message> getSubscribe(@RequestBody SubscribersId petitionInfo,
                                       @PathVariable("id") long id, HttpServletRequest request){

        User subscriber = this.userRepositoryService.getUserFromRequest(request);
        if(subscriber == null){
            this.message.setAnswer("User not found. Your token invalid. Access denied");
            return new ResponseEntity<Message>(message, HttpStatus.FORBIDDEN);
        }
        Petition petition = this.petitionRepositoryService.findById(id);
        if(petition == null){
            this.message.setAnswer("Petition not found. ");
            return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
        }
        petition.incrementCountSign();
        System.out.println(petitionInfo.getAnon());
        this.petitionRepositoryService.save(petition);
        subscribers.settingSubscriberObject(petition, subscriber, petitionInfo.getAnon());
        if(this.subscribersRepositoryService.save(subscribers)){
            this.message.setAnswer("Subscribed");
            return new ResponseEntity<Message>(message, HttpStatus.CREATED);
        }
        this.message.setAnswer("You are already subscribed");
        return new ResponseEntity<Message>(message, HttpStatus.CONFLICT);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Petition> getPetitionById(@PathVariable("id") long id){
        Petition petition = this.petitionRepositoryService.findById(id);
        if(petition != null){
            return new ResponseEntity<Petition>(petition, HttpStatus.OK);
        }else{
            return new ResponseEntity<Petition>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}/users")
    public ResponseEntity<ArrayList<UserWrapper>> getAllSubscribedUser(@PathVariable("id") long id){
        ArrayList<Subscribers> subscribers = this.subscribersRepositoryService.findAllByPetitionId(id);
        if(subscribers == null){
            return new ResponseEntity<ArrayList<UserWrapper>>(HttpStatus.NOT_FOUND);
        }
        ArrayList<UserWrapper> users = new ArrayList<>();
        for(Subscribers sub : subscribers){
            if(sub.getAnon()){
                UserWrapper anonUserWrapper = createUserWrapper();
                anonUserWrapper.settingWrapperUserAnon();
                users.add(anonUserWrapper);

            }else{
                User user = this.userRepositoryService.findById(sub.getUser().getID());
                if (user == null){
                    continue;
                }else{
                    UserWrapper userWrapper = createUserWrapper();
                    userWrapper.settingWrapperUser(sub.getUser().getSurname(), sub.getUser().getName(), sub.getUser().getID());
                    users.add(userWrapper);
                }
            }
        }
        return new ResponseEntity<ArrayList<UserWrapper>>(users, HttpStatus.OK);
    }
}
