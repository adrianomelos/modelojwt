package com.modelo.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.modelo.entities.PrivacyPolicy;
import com.modelo.exceptions.DatabaseException;
import com.modelo.exceptions.ResourceNotFoundException;
import com.modelo.repository.PrivacyPolicyRepository;

@Service
public class PrivacyPolicyService {

	@Autowired
	private PrivacyPolicyRepository privacyPolicyRepository;

	public void delete(Long id) {
		try {
			privacyPolicyRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new DatabaseException("Não foi possível remover cód. " + id);
		}
	}

	public PrivacyPolicy save(PrivacyPolicy privacyPolicy) {
		return privacyPolicyRepository.save(privacyPolicy);
	}

	public PrivacyPolicy findPrivacyPolicy() {
		Optional<PrivacyPolicy> obj = privacyPolicyRepository.findPrivacyPolicy();
		return obj.orElseThrow(() -> new ResourceNotFoundException("não há politica de privacidade cadastrada"));
	}

	public PrivacyPolicy update(@Valid Long id, PrivacyPolicy privacyPolicy) {
		Optional<PrivacyPolicy> obj = privacyPolicyRepository.findById(id);
		obj.orElseThrow(() -> new ResourceNotFoundException("não há politica de privacidade cadastrada"));
		obj.get().setDescription(privacyPolicy.getDescription());
		return privacyPolicyRepository.save(obj.get());
	}

}
