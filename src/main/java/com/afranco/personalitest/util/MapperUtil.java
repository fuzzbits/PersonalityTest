package com.afranco.personalitest.util;

import java.util.List;
import java.util.stream.Collectors;

import com.afranco.personalitest.model.AnswerData;
import com.afranco.personalitest.model.QuestionData;
import com.afranco.personalitest.model.entity.AnswerEntity;
import com.afranco.personalitest.model.entity.QuestionEntity;

public class MapperUtil {

	public MapperUtil() {}
	
	public static QuestionData mapQuestionData(QuestionEntity questionEntity, List<AnswerEntity> answerEntityList) {
		
		List<AnswerData> answers = answerEntityList.stream().map(MapperUtil::mapAnswerData).collect(Collectors.toList());

		QuestionData data = new QuestionData();
		data.setQuestion(questionEntity.getQuestion());
		data.setTestOrder(questionEntity.getTestOrder());
		data.setAnswers(answers);
		
		return data;
	}
	
	public static AnswerData mapAnswerData(AnswerEntity entity) {
		
		QuestionEntity questionEntity = entity.getQuestion();
		
		AnswerData data = new AnswerData();
		data.setAnswer(entity.getAnswer());
		data.setIntrovert(entity.isIntrovert());
		data.setTestChar(entity.getTestChar());
		data.setUniqueId(String.format("%s%s", questionEntity.getId(), entity.getTestChar()));
		
		return data;
	}
}
