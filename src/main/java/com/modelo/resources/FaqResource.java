package com.modelo.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.entities.Faq;
import com.modelo.services.FaqService;

@RestController
@RequestMapping(value = "/faq")
public class FaqResource {

	@Autowired
	private FaqService faqService;

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		faqService.delete(id);
	}
	
	@PostMapping
	public Faq save(@RequestBody Faq faq ) {
		return faqService.save(faq);
	}
	
	@GetMapping
	public Page<Faq> findAll(
			@RequestParam(required = true, defaultValue = "10") int qtd,
			@RequestParam(required = true, defaultValue = "0") int page,
			@RequestParam(value = "nome", required = false) Optional<String> question) {
		Pageable paginacao = PageRequest.of(page, qtd);
		return faqService.findAll(paginacao, question.orElse(null));
	}

}
