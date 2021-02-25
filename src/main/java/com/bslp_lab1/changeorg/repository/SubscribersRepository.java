package com.bslp_lab1.changeorg.repository;

import com.bslp_lab1.changeorg.beans.Subscribers;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface SubscribersRepository extends CrudRepository<Subscribers, Long> {
    ArrayList<Subscribers> findAllByPetitionID(Long aLong);

}
