package com.afranco.personalitest.model.entity;

import com.afranco.personalitest.util.CharToBooleanConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ANSWER")
public class AnswerEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "QUESTION_FK")
	private QuestionEntity question;
	
	@Column(name = "ANSWER")
	private String answer;
	
	@Column(name = "IS_INTROVERT")
    @Convert(converter = CharToBooleanConverter.class)
	private boolean introvert;
	
	@Column(name = "TEST_CHAR")
	private String testChar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuestionEntity getQuestion() {
		return question;
	}

	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isIntrovert() {
		return introvert;
	}

	public void setIntrovert(boolean introvert) {
		this.introvert = introvert;
	}

	public String getTestChar() {
		return testChar;
	}

	public void setTestChar(String testChar) {
		this.testChar = testChar;
	}

}