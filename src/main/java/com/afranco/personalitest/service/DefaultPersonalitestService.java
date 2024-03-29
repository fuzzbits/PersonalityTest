package com.afranco.personalitest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.afranco.personalitest.model.AnswerData;
import com.afranco.personalitest.model.entity.AnswerEntity;
import com.afranco.personalitest.model.entity.QuestionEntity;
import com.afranco.personalitest.repository.AnswerRepository;
import com.afranco.personalitest.repository.QuestionRepository;
import com.afranco.personalitest.util.MapperUtil;

/***
 * Main service for Personalitest
 * 
 * @author André Franco
 *
 */
@Service
public class DefaultPersonalitestService implements PersonalitestService {
	
	private AnswerRepository answerRepository;
	
	private QuestionRepository questionRepository;
	 
	public DefaultPersonalitestService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
		this.answerRepository = answerRepository;
		this.questionRepository = questionRepository;
	}


	@Override
	public Map<String, Object> handleTest(String answers) {
		
		Map<String, Object> response = new HashMap<>();
		
		int totalQuestions = countQuestions();
 		int nextTestOrder = getNextQuestionTestOrder(answers);
		
		if(answers != null && answers.length()>totalQuestions) {
			return response;
		}else if(nextTestOrder<= totalQuestions) {
			QuestionEntity nextQuestion =  getQuestionEntityByTestOrder(nextTestOrder);
			List<AnswerEntity> answersEntity = getAnswerEntityById(nextQuestion.getId());
			response.put("question", MapperUtil.mapQuestionData(nextQuestion, answersEntity));
		}else {
			double introvertPercentage = ((double)countIntrovertAnswers(answers)/totalQuestions*100);
			response.put("result", parsePercentageResult(introvertPercentage));
		}
		
		return response;
		
	}
	
	private String parsePercentageResult(double introvertPercentage) {
		
		String result = introvertPercentage>50 ? "Introvert" : "Extrovert";
		double percentage = introvertPercentage>50 ? introvertPercentage : 100-introvertPercentage;
		
		return String.format("%2.02f%% %s!",percentage,result);
	}
	
	private int countIntrovertAnswers(String answers) {
		List<AnswerData> answersList = getAllAnswersEntities().stream()
				.map(MapperUtil::mapAnswerData).collect(Collectors.toList());
		
		Map<String,Boolean> answersData = answersList.stream().collect(Collectors.toMap(AnswerData::getUniqueId, AnswerData::isIntrovert));
		
		int countIntrovertAnswers = 0;
		
		for(int i=0; i<answers.length();i++) {
			String uniqueId = String.format("%s%s", (i+1) , answers.charAt(i));
			if(answersData.get(uniqueId)) {
				countIntrovertAnswers++;
			}
		}
		
		return countIntrovertAnswers;
	}
	
	
	private int getNextQuestionTestOrder(String answers) {
		return answers!=null ? answers.length()+1 : 1;
	}
	
	private QuestionEntity getQuestionEntityByTestOrder(int testOrder) {
		return questionRepository.getQuestionByTestOrder(testOrder);
	}
	
	private List<AnswerEntity> getAnswerEntityById(Long id) {
		return answerRepository.getAnswersByQuestionId(id);
	}
	
	private List<AnswerEntity> getAllAnswersEntities(){
		return answerRepository.findAll();
	}
	
	private int countQuestions() {
		return Math.toIntExact(questionRepository.count());
	}

}
