package com.modelo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.modelo.entities.Faq;
import com.modelo.exceptions.DatabaseException;
import com.modelo.exceptions.ResourceNotFoundException;
import com.modelo.repository.FaqRepository;

@Service
public class FaqService {
	
	@Autowired
	private FaqRepository faqRepository;

	public void delete(Long id) {
		try {
			faqRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new DatabaseException("");
		}
	}

	public Faq save(Faq faq) {
		return faqRepository.save(faq);
	}

	public Page<Faq> findAll(Pageable paginacao, String question) {
		
		Page<Faq> list;
		if (question == null) {
			list = faqRepository.findAll(paginacao);
		} else {
			list = faqRepository.findByQuestionLike("%" + question + "%", paginacao);
		}
		return list;
	}
}
