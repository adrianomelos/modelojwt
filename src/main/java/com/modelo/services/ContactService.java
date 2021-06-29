package com.modelo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.modelo.entities.Contact;
import com.modelo.exceptions.DatabaseException;
import com.modelo.exceptions.ResourceNotFoundException;
import com.modelo.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;

	public void delete(Long id) {
		try {
			contactRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new DatabaseException("");
		}
	}

	public Contact save(Contact contact) {
		contact.setDateCreate(LocalDateTime.now());
		return contactRepository.save(contact);
	}

	public Optional<Contact> findById(Long id) {
		return contactRepository.findById(id);
	}

	public List<Contact> findAll() {
		return contactRepository.findAll();
	}
}
