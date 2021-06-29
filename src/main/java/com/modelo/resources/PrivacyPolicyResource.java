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

import com.modelo.entities.PrivacyPolicy;
import com.modelo.services.PrivacyPolicyService;

@RestController
@RequestMapping(value = "/privacy_policy")
public class PrivacyPolicyResource {
	
	@Autowired
	private PrivacyPolicyService privacyPolicyService;
	
	@GetMapping
	public PrivacyPolicy findPrivacyPolicy() {
		return privacyPolicyService.findPrivacyPolicy();
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		privacyPolicyService.delete(id);
	}
	
	@PostMapping
	public PrivacyPolicy save(@RequestBody PrivacyPolicy privacyPolicy ) {
		return privacyPolicyService.save(privacyPolicy);
	}
	
	@PutMapping(value = "/{id}")
	public PrivacyPolicy update(@Valid @PathVariable Long id, @RequestBody PrivacyPolicy privacyPolicy) {
		privacyPolicy = privacyPolicyService.update(id, privacyPolicy);
		return privacyPolicy;
	}
}
