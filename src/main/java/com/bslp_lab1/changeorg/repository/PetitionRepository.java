package com.bslp_lab1.changeorg.repository;

import com.bslp_lab1.changeorg.beans.Petition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetitionRepository extends CrudRepository<Petition, Long> {
    @Override
    Optional<Petition> findById(Long aLong);
    @Override
    List<Petition> findAll();

}
