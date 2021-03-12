package com.bslp_lab1.changeorg.repository;

import com.bslp_lab1.changeorg.beans.Petition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetitionRepository extends CrudRepository<Petition, Long> {
    Petition findByID(Long id);

    @Override
    List<Petition> findAll();

}
