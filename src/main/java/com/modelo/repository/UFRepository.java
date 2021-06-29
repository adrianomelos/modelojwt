package com.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.entities.UF;

@Repository
public interface UFRepository extends JpaRepository<UF, Long> {

}
