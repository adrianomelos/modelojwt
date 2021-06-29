package com.modelo.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.entities.Roles;
import com.modelo.services.RolesService;

@RestController
@RequestMapping(value = "/roles")
public class RolesResource {
	
	@Autowired
	private RolesService rolesService;
	
	@GetMapping
	public List<Roles> findAll(){
		return rolesService.findAll();		
	}
	
	@GetMapping(value = "/{id}")
	public Roles findById(@PathVariable Long id) {
		return rolesService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Roles save(@Valid @RequestBody Roles roles) {
		return rolesService.save(roles);
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		rolesService.delete(id);
	}
	
	@PutMapping(value = "/{id}")
	public Roles update(@Valid @PathVariable Long id, @RequestBody Roles roles) {
		roles = rolesService.update(id, roles);
		return roles;
	}

}
