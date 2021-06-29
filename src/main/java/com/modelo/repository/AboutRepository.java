package com.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.modelo.entities.About;

@Repository
public interface AboutRepository extends JpaRepository<About, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM `tb_about` WHERE id ORDER by id DESC limit 1")
	Optional<About> findAbout();

}
