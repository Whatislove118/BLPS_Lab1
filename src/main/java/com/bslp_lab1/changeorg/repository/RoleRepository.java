package com.bslp_lab1.changeorg.repository;

import com.bslp_lab1.changeorg.beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
