package com.modelo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String email);

	Page<User> findByUsernameLike(String string, Pageable paginacao);

	Optional<User> findBySecurityToken(String token);

	Optional<User> findByHash(String hash);

}
