package com.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	List<City> findByUfId(Long uf);

}
