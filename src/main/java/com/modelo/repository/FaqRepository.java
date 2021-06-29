package com.modelo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.entities.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

	Page<Faq> findByQuestionLike(String string, Pageable paginacao);

}
