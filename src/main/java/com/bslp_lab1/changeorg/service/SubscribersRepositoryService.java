package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.DTO.UserDTO;
import com.bslp_lab1.changeorg.beans.Petition;
import com.bslp_lab1.changeorg.beans.Subscribers;
import com.bslp_lab1.changeorg.beans.User;
import com.bslp_lab1.changeorg.exceptions.PetitionNotFoundException;
import com.bslp_lab1.changeorg.exceptions.UserNotFoundException;
import com.bslp_lab1.changeorg.repository.SubscribersRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubscribersRepositoryService {

    @Autowired
    private SubscribersRepository subscribersRepository;
    @Autowired
    private Subscribers subscribers;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private UserRepositoryService userRepositoryService;



    public SubscribersRepository getSubscribersRepository() {
        return subscribersRepository;
    }

    public void setSubscribersRepository(SubscribersRepository subscribersRepository) {
        this.subscribersRepository = subscribersRepository;
    }

    public void save(Subscribers subscriber) throws DataIntegrityViolationException {
        this.subscribersRepository.save(subscriber);
    }

    public ResponseEntity getAllSubscribedUsers(Long id) throws UserNotFoundException, PetitionNotFoundException{
        ArrayList<Subscribers> subscribers;
        try {
            subscribers = this.findAllByPetitionId(id);
        }catch (PetitionNotFoundException e){
            throw e;
        }
        ArrayList<UserDTO> users = new ArrayList<>();
        for(Subscribers sub : subscribers){
            if(sub.getAnon()){
                UserDTO userDTO = dtoConverter.convertAnonUserToDTO();
                userDTO.settingWrapperUserAnon();
                users.add(userDTO);
            }else{
                try{
                    User user = this.userRepositoryService.findById(sub.getUser().getID());
                    UserDTO userDTO = dtoConverter.convertUserToDTO(user);
                    userDTO.settingWrapperUser(sub.getUser().getSurname(), sub.getUser().getName(), sub.getUser().getID());
                    users.add(userDTO);
                }catch (UserNotFoundException e){
                    throw e;
                }

            }
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> subscribe(Petition petition, User subscriber, Boolean anon){
        subscribers.settingSubscriberObject(petition, subscriber, anon);
        ResponseMessageDTO message = new ResponseMessageDTO();
        try {
            this.save(subscribers);
            message.setAnswer("Subscribed");
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            String answerText = "";
            if(e.getCause().getClass() == ConstraintViolationException.class){
                answerText = "Вы уже подписаны!";
            }else{
                answerText = "УПС! Произошла ошибка, пожалуйста, попробуйте позднее";
            }
            message.setAnswer(answerText);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }


    public ArrayList<Subscribers> findAllByPetitionId(Long id) throws PetitionNotFoundException {
            ArrayList<Subscribers> subscribers = subscribersRepository.findAllByPetitionID(id);
            if(subscribers.isEmpty()){
                throw new PetitionNotFoundException("У данной петиции нет подписчиков", HttpStatus.BAD_REQUEST);
            }
            return subscribers;
        }

}
