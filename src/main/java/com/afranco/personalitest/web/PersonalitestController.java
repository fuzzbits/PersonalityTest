package com.afranco.personalitest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.afranco.personalitest.service.PersonalitestService;

@Controller
public class PersonalitestController {

	@Autowired
	private PersonalitestService personalitiesService;
	
	@GetMapping("/personalitest")
	public String personalitest(@RequestParam(name="answers", required=false) String answers, Model model) {
		return personalitiesService.handleTest(answers, model);
	}
}
