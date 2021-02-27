package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.DTO.PetitionDTO;
import com.bslp_lab1.changeorg.beans.Petition;
import com.bslp_lab1.changeorg.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PetitionRepositoryService{

    @Autowired
    private PetitionRepository petitionRepository;


    public List<Petition> findAll(){
        return this.petitionRepository.findAll();
    }

    public Petition findById(Long id) throws NullPointerException{
        return this.petitionRepository.findById(id).get();
    }

    public void save(Petition petition) throws DataIntegrityViolationException {
        this.petitionRepository.save(petition);
    }


    public PetitionRepository getPetitionRepository() {
        return petitionRepository;
    }
}
