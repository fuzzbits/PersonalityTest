package com.afranco.personalitest.service;

import org.springframework.ui.Model;

public interface PersonalitestService {

	String handleTest(String answers, Model model);
	
}
