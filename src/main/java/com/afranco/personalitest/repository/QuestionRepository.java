package com.afranco.personalitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.afranco.personalitest.model.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>{

	@Query("select q from QuestionEntity q where q.testOrder=?1")
	QuestionEntity getQuestionByTestOrder(int testOrder);	
}
