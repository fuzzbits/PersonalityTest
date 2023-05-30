package com.afranco.personalitest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.afranco.personalitest.model.entity.AnswerEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long>{

	@Query("select a from AnswerEntity a where a.question.id=?1")
	List<AnswerEntity> getAnswersByQuestionId(Long questionId);	
}
