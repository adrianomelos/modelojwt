package com.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.entities.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

}
