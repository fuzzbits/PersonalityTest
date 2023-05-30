package com.afranco.personalitest.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.afranco.personalitest.model.AnswerData;
import com.afranco.personalitest.model.entity.AnswerEntity;
import com.afranco.personalitest.model.entity.QuestionEntity;
import com.afranco.personalitest.repository.AnswerRepository;
import com.afranco.personalitest.repository.QuestionRepository;
import com.afranco.personalitest.util.MapperUtil;

@Service
public class DefaultPersonalitestService implements PersonalitestService {

	private int TOTAL_QUESTIONS;
	
	private AnswerRepository answerRepository;
	
	private QuestionRepository questionRepository;
	
	public DefaultPersonalitestService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
		this.answerRepository = answerRepository;
		this.questionRepository = questionRepository;
		TOTAL_QUESTIONS = (int) questionRepository.count();
	}


	@Override
	public String handleTest(String answers, Model model) {
		
		int nextTestOrder = getNextQuestionTestOrder(answers);
		
		if(answers != null && answers.length()>TOTAL_QUESTIONS) {
			return "error";
		}
		
		if(nextTestOrder<= TOTAL_QUESTIONS) {
			QuestionEntity nextQuestion =  questionRepository.getQuestionByTestOrder(nextTestOrder);
			List<AnswerEntity> answersEntity = answerRepository.getAnswersByQuestionId(nextQuestion.getId());
			model.addAttribute("question", MapperUtil.mapQuestionData(nextQuestion, answersEntity));
			return "personalitest";
		}else {
			model.addAttribute("result", String.format("%2.02f %% Introvert!",getIntrovertPercentage(answers)));
			return "end_test";
		}
		
	}
	
	private float getIntrovertPercentage(String answers) {
				List<AnswerData> answersList = answerRepository.findAll().stream()
				.map(MapperUtil::mapAnswerData).collect(Collectors.toList());
		
		Map<String,Boolean> answersData = answersList.stream().collect(Collectors.toMap(AnswerData::getUniqueId, AnswerData::isIntrovert));
		
		int countIntrovertAnswers = 0;
		
		for(int i=0; i<answers.length();i++) {
			String uniqueId = String.format("%s%s", (i+1) , answers.charAt(i));
			if(answersData.get(uniqueId)) {
				countIntrovertAnswers++;
			}
		}
		
		return ((float)countIntrovertAnswers/TOTAL_QUESTIONS*100);
	}
	
	
	private int getNextQuestionTestOrder(String answers) {
		return answers!=null ? answers.length()+1 : 1;
	}

}
