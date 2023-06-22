package com.afranco.personalitest.web;

import java.util.Map;

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
 
	/***
	 * Displays Personalitest Home Route
	 * 
	 * @returns template name
	 */
	@GetMapping
	public String home() {
		return "index";
	}
	
	/***
	 * Displays Personalitest Main Pages according to the answers.
	 * 
	 * @param answers test answers (Optional)
	 * @param model  spring model
	 * 
	 * @returns template name
	 */
	@GetMapping("/personalitest")
	public String personalitest(@RequestParam(name="answers", required=false) String answers, Model model) {
		Map<String, Object> serviceResponse =  personalitestService.handleTest(answers);

		model.addAllAttributes(serviceResponse);
		
		return resolveTemplateByServiceMap(serviceResponse);
	}
	
	private String resolveTemplateByServiceMap(Map<String, Object> map) {
		
		if(map.size()==0) {
			return "error";
		}else{
			String key = map.keySet().iterator().next();
			if("question".equals(key)) {
				return "personalitest";
			}else {
				return "end_test";
			}
		}
	}
}
