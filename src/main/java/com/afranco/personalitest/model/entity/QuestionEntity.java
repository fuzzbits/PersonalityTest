package com.afranco.personalitest.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="QUESTION")
public class QuestionEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "QUESTION")
	private String question;
	
	@Column(name = "TEST_ORDER")
	private Integer testOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getTestOrder() {
		return testOrder;
	}

	public void setTestOrder(Integer testOrder) {
		this.testOrder = testOrder;
	} 
	
}
