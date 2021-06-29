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

import com.modelo.entities.About;
import com.modelo.services.AboutService;

@RestController
@RequestMapping(value = "/about")
public class AboutResource {

	@Autowired
	private AboutService aboutService;

	@GetMapping
	public About findAbout() {
		return aboutService.findAbout();
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		aboutService.delete(id);
	}
	
	@PostMapping
	public About save(@RequestBody About about ) {
		return aboutService.save(about);
	}

	@PutMapping(value = "/{id}")
	public About update(@Valid @PathVariable Long id, @RequestBody About about) {
		about = aboutService.update(id, about);
		return about;
	}
}
