package com.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
