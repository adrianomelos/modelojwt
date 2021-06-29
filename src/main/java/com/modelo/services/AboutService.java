package com.modelo.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.modelo.entities.About;
import com.modelo.exceptions.DatabaseException;
import com.modelo.exceptions.ResourceNotFoundException;
import com.modelo.repository.AboutRepository;

@Service
public class AboutService {

	@Autowired
	private AboutRepository aboutRepository;

	public void delete(Long id) {
		try {
			aboutRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new DatabaseException("");
		}
	}

	public About save(About about) {
		return aboutRepository.save(about);
	}

	public About findAbout() {
		Optional<About> obj = aboutRepository.findAbout();
		return obj.orElseThrow(() -> new ResourceNotFoundException("Nenhum texto Sobre o App cadastrado"));
		
	}

	public About update(@Valid Long id, About about) {
		Optional<About> obj =  aboutRepository.findById(id);
		obj.get().setDescription(about.getDescription());
		return aboutRepository.save(obj.get());
	}
}
