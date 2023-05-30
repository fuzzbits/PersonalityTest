package com.afranco.personalitest.model;

import java.util.List;

public class QuestionData {

	private String question;
	
	private int testOrder;
	
	private List<AnswerData> answers;
	

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getTestOrder() {
		return testOrder;
	}

	public void setTestOrder(int testOrder) {
		this.testOrder = testOrder;
	}

	public List<AnswerData> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerData> answers) {
		this.answers = answers;
	}
	
}
