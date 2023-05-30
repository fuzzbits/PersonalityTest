package com.afranco.personalitest.model;

public class AnswerData {

	private String uniqueId;
	
	private String answer;
	
	private boolean isIntrovert;
	
	private String testChar;

	public String getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(String uniqueId) {
		this.uniqueId=uniqueId;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isIntrovert() {
		return isIntrovert;
	}

	public void setIntrovert(boolean isIntrovert) {
		this.isIntrovert = isIntrovert;
	}

	public String getTestChar() {
		return testChar;
	}

	public void setTestChar(String testChar) {
		this.testChar = testChar;
	}
	
}
