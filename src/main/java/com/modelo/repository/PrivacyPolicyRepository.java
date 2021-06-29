package com.modelo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.modelo.entities.PrivacyPolicy;

@Repository
public interface PrivacyPolicyRepository extends JpaRepository<PrivacyPolicy, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM `tb_privacy_policy` WHERE id ORDER by id DESC limit 1")
	Optional<PrivacyPolicy> findPrivacyPolicy();

}
