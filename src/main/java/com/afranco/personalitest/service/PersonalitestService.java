package com.afranco.personalitest.service;

import java.util.Map;

public interface PersonalitestService {

	/***
	 * Handles the test based on answers.
	 * 
	 * @param answers test answers
	 * @return Map containing attributes to be added to the spring model
	 */
	Map<String, Object> handleTest(String answers);
	
}
