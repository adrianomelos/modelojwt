package com.modelo.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.entities.City;
import com.modelo.services.CityService;

@CrossOrigin
@RestController
@RequestMapping(value = "/city")
public class CityResource {

	@Autowired
	private CityService citySevice;

	@GetMapping
	public List<City> findCityByUF(@RequestParam(value = "uf", required = true) Long uf) {
		return citySevice.findByCityUF(uf);
	}

	@GetMapping(value = "/{id}")
	public Optional<City> findById(@PathVariable Long id) {
		return citySevice.findByCityId(id);
	}
}
