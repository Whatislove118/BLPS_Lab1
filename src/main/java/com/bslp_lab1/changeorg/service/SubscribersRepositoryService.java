package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.Subscribers;
import com.bslp_lab1.changeorg.repository.SubscribersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubscribersRepositoryService {

    @Autowired
    private SubscribersRepository subscribersRepository;


    public SubscribersRepository getSubscribersRepository() {
        return subscribersRepository;
    }

    public void setSubscribersRepository(SubscribersRepository subscribersRepository) {
        this.subscribersRepository = subscribersRepository;
    }

    public boolean save(Subscribers subscriber){
        try{
            this.subscribersRepository.save(subscriber);
            return true;
        }catch (Exception e){
            System.out.println("Subscriber already exists!");
            return false;
        }
    }

    public ArrayList<Subscribers> findAllByPetitionId(Long id){
        try{
            return subscribersRepository.findAllByPetitionID(id);
        }catch (Exception e){
            System.out.println("Subscribers not found");
            return null;
        }

    }
}
