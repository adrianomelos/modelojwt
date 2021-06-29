package com.modelo.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.modelo.entities.TermsUse;
import com.modelo.exceptions.DatabaseException;
import com.modelo.exceptions.ResourceNotFoundException;
import com.modelo.repository.TermsUseRepository;

@Service
public class TermsUseService {

	@Autowired
	private TermsUseRepository termsUseRepository;

	public void delete(Long id) {
		try {
			termsUseRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new DatabaseException("");
		}
	}

	public TermsUse findTermsUse() {
		Optional<TermsUse> obj = termsUseRepository.findTermsUse();
		return obj.orElseThrow(() -> new ResourceNotFoundException("não há termos de usu cadastrado"));
	}

	public TermsUse save(TermsUse termsUse) {
		return termsUseRepository.save(termsUse);
	}

	public TermsUse update(@Valid Long id, TermsUse termsUse) {
		Optional<TermsUse> obj = termsUseRepository.findById(id);
		obj.orElseThrow(() -> new ResourceNotFoundException("Termos de uso não encontrado"));
		obj.get().setDescription(termsUse.getDescription());
		return termsUseRepository.save(obj.get());
	}

}
