package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.DTO.SubscribersDTO;
import com.bslp_lab1.changeorg.DTO.UserDTO;
import com.bslp_lab1.changeorg.beans.Petition;
import com.bslp_lab1.changeorg.beans.Subscribers;
import com.bslp_lab1.changeorg.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DTOConverter {

    /*  This code about Convert TO DTO  */
    @Autowired
    private PetitionDTO petitionDTO;
    @Autowired
    private UserDTO userDTO;


    public PetitionDTO convertPetitionToDTO(Petition petition){
        petitionDTO.setID(petition.getID());
        petitionDTO.setTopic(petition.getTopic());
        petitionDTO.setCountSign(petition.getCountSign());
        petitionDTO.setDescription(petition.getDescription());
        petitionDTO.setImageSrc(petition.getImageSrc());
        petitionDTO.setSignGoal(petition.getSignGoal());
        petitionDTO.setOwner(this.convertUserToDTO(petition.getOwner()));
        return petitionDTO;
    }

    public UserDTO convertUserToDTO(User user){
        userDTO.setId(user.getID());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        return userDTO;
    }

    public UserDTO convertAnonUserToDTO(){
        userDTO.setId(null);
        userDTO.setName("Анон");
        userDTO.setSurname("Анончик");
        return userDTO;
    }

    public ArrayList<UserDTO> convertSubscribersToUserDTO(ArrayList<Subscribers> subscribers, User user) {
        ArrayList<UserDTO> userDTOArrayList = new ArrayList<>();
        for (Subscribers sub : subscribers) {
            if (sub.getAnon()) {
                userDTO = convertUserToDTO(user);
                userDTOArrayList.add(userDTO);
            } else {
                if (user == null) {
                    continue;
                } else {
                    userDTO = this.convertUserToDTO(user);
                    userDTOArrayList.add(userDTO);
                }
            }
        }
        return userDTOArrayList;
    }


    /*  This code about convert FROM DTO  */
    @Autowired
    private User user;
    @Autowired
    private Petition petition;
    @Autowired
    private Subscribers subscribers;



    public User convertUserFromDTO(UserDTO userDTO){
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if(userDTO.getId() != null){
            userDTO.setId(userDTO.getId());
        }
        return user;
    }

    public Petition convertPetitionFromDTO(PetitionDTO petitionDTO, User user){
        petition.setOwner(user);
        petition.setCountSign(0);
        petition.setDescription(petitionDTO.getDescription());
        petition.setImageSrc(petitionDTO.getImageSrc());
        petition.setTopic(petitionDTO.getTopic());
        petition.setSignGoal(petitionDTO.getSignGoal());
        if(petitionDTO.getID() != null){
            userDTO.setId(petitionDTO.getID());
        }
        return petition;
    }




}
