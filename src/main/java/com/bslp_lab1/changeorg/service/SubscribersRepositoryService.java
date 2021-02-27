package com.bslp_lab1.changeorg.service;

import com.bslp_lab1.changeorg.beans.Subscribers;
import com.bslp_lab1.changeorg.repository.SubscribersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public void save(Subscribers subscriber) throws DataIntegrityViolationException {
        this.subscribersRepository.save(subscriber);
    }

    public ArrayList<Subscribers> findAllByPetitionId(Long id) throws NullPointerException{
            return subscribersRepository.findAllByPetitionID(id);
        }

}
