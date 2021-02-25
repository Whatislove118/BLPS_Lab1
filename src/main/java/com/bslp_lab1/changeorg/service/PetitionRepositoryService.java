package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.Petition;
import com.bslp_lab1.changeorg.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PetitionRepositoryService{

    @Autowired
    private PetitionRepository petitionRepository;


    public Iterable<Petition> findAll(){
        return this.petitionRepository.findAll();
    }

    public Petition findById(Long id){
        try{
            return this.petitionRepository.findById(id).get();
        }catch (Exception e){
            System.out.println("Petition not found");
            return null;
        }
    }

    public boolean save(Petition petition){
        try{
            this.petitionRepository.save(petition);
            return true;
        }catch (Exception e){
            System.out.println("Can't save petition. Petition with same topic already exists");
            return false;
        }
    }


    public PetitionRepository getPetitionRepository() {
        return petitionRepository;
    }
}
