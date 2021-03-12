package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.DTO.ResponseMessageDTO;
import com.bslp_lab1.changeorg.beans.Petition;
import com.bslp_lab1.changeorg.beans.User;
import com.bslp_lab1.changeorg.exceptions.PetitionNotFoundException;
import com.bslp_lab1.changeorg.exceptions.UserNotFoundException;
import com.bslp_lab1.changeorg.repository.PetitionRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
public class PetitionRepositoryService{

    @Autowired
    private PetitionRepository petitionRepository;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private DTOConverter dtoConverter;





    public List<Petition> findAll(){
        return this.petitionRepository.findAll();
    }

    public Petition findById(Long id) throws PetitionNotFoundException{
        Petition petition = this.petitionRepository.findByID(id);
        if(petition == null){
            throw new PetitionNotFoundException("Петиция не найдена", HttpStatus.BAD_REQUEST);
        }
        return petition;

    }


    public PetitionDTO findByIdToResponse(Long id) throws PetitionNotFoundException{
        Petition petition = this.findById(id);
        PetitionDTO petitionDTO = dtoConverter.convertPetitionToDTO(petition);
        return petitionDTO;
    }
    public ArrayList<PetitionDTO> getAllPetitions(){
        ArrayList<PetitionDTO> petitionDTOS = new ArrayList<>();
        ArrayList<Petition> petitions = new ArrayList<>(this.findAll());
        for (Petition petition : petitions){
            System.out.println(petition.getID());
            petitionDTOS.add(dtoConverter.convertPetitionToDTO(petition));
        }
        System.out.println(petitionDTOS.get(0).getID());
        System.out.println(petitionDTOS.get(1).getID());
        return petitionDTOS;
    }

    public ResponseEntity<ResponseMessageDTO> saveFromDTO(PetitionDTO petitionDTO, HttpServletRequest request){
        ResponseMessageDTO message = new ResponseMessageDTO();
        try {
            User owner = this.userRepositoryService.getUserFromRequest(request);
            Petition petition = dtoConverter.convertPetitionFromDTO(petitionDTO, owner);
            this.petitionRepository.save(petition);
            message.setAnswer("Petition was added");
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            message.setAnswer("Petition without owner. Please, try later");
            return new ResponseEntity<>(message, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        } catch (DataIntegrityViolationException e) {
            String answerText = "";
            if(e.getCause().getClass() == ConstraintViolationException.class){
                answerText = "Петиция с таким топиком уже существует!";
            }else{
                answerText = "УПС! Произошла ошибка, пожалуйста, попробуйте позднее";
            }
            message.setAnswer(answerText);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    public void save(Petition petition){
        this.petitionRepository.save(petition);
    }

    public void incrementCountSign(Petition petition){
        petition.incrementCountSign();
        this.save(petition);
    }

    public PetitionRepository getPetitionRepository() {
        return petitionRepository;
    }
}
