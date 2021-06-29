package com.modelo.resources;

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

import com.modelo.entities.TermsUse;
import com.modelo.services.TermsUseService;

@RestController
@RequestMapping(value = "/terms_use")
public class TermsUseResource {
	
	@Autowired
	private TermsUseService termsUseService;
	
	@GetMapping
	public TermsUse findTermsUse() {
		return termsUseService.findTermsUse();
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		termsUseService.delete(id);
	}
	
	@PostMapping
	public TermsUse save(@RequestBody TermsUse termsUse ) {
		return termsUseService.save(termsUse);
	}
	
	@PutMapping(value = "/{id}")
	public TermsUse update(@Valid @PathVariable Long id, @RequestBody TermsUse termsUse) {
		termsUse = termsUseService.update(id, termsUse);
		return termsUse;
	}
}
