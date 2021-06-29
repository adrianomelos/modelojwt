package com.modelo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.modelo.entities.Roles;
import com.modelo.exceptions.DatabaseException;
import com.modelo.exceptions.ResourceNotFoundException;
import com.modelo.repository.RolesRepository;

@Service
public class RolesService {

	@Autowired
	private RolesRepository rolesRepository;

	public List<Roles> findAll() {
		return rolesRepository.findAll();
	}

	public Roles findById(Long id) {
		Optional<Roles> obj = rolesRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void delete(Long id) {
		try {
			rolesRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new DatabaseException("");
		}
	}

	public Roles save(Roles roles) {
		return rolesRepository.save(roles);
	}

	public Roles update(Long id, Roles roles) {
		Roles entity = rolesRepository.getOne(id);
		update(entity, roles);
		return rolesRepository.save(entity);
	}

	private void update(Roles entity, Roles roles) {
		
	}

}
