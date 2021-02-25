package com.bslp_lab1.changeorg.repository;

import com.bslp_lab1.changeorg.beans.User;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findByID(Long id);
}
