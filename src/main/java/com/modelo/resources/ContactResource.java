package com.modelo.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.entities.Contact;
import com.modelo.services.ContactService;

@RestController
@RequestMapping(value = "/contact")
public class ContactResource {

	@Autowired
	private ContactService contactService;

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		contactService.delete(id);
	}

	@PostMapping
	public Contact save(@RequestBody Contact contact) {
		return contactService.save(contact);
	}
	
	@GetMapping(value = "/{id}")
	public Optional<Contact> findById(@PathVariable Long id){
		return contactService.findById(id);
	}
	
	@GetMapping
	public List<Contact> findAll(){
		return contactService.findAll();
	}
}
