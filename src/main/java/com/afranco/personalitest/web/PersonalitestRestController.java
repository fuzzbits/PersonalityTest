package com.afranco.personalitest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.afranco.personalitest.service.PersonalitestService;

@Controller
public class PersonalitestRestController {

	private PersonalitestService personalitestService;
	
	public PersonalitestRestController(PersonalitestService personalitestService) {
		this.personalitestService = personalitestService;
	}

	@GetMapping
	public String home() {
		return "index.html";
	}
	
	@GetMapping("/personalitest")
	public String personalitest(@RequestParam(name="answers", required=false) String answers, Model model) {
		return personalitestService.handleTest(answers, model);
	}
}
