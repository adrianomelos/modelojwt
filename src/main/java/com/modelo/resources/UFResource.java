package com.modelo.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.entities.UF;
import com.modelo.services.UFService;

@CrossOrigin
@RestController
@RequestMapping(value = "/uf")
public class UFResource {

	@Autowired
	private UFService ufSevice;

	@GetMapping
	public List<UF> findAll() {
		return ufSevice.findAllUf();
	}

	@GetMapping(value = "/{idUf}")
	public Optional<UF> findById(@PathVariable Long idUf) {
		return ufSevice.findByUfId(idUf);
	}
}
