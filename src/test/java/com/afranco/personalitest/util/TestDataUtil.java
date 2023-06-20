package com.afranco.personalitest.util;

import java.util.ArrayList;
import java.util.List;

import com.afranco.personalitest.model.entity.AnswerEntity;
import com.afranco.personalitest.model.entity.QuestionEntity;

public class TestDataUtil {

	
    public static QuestionEntity createQuestionEntity(int testOrder) {
   	 
   	 	QuestionEntity question = new QuestionEntity();
        question.setId(Long.valueOf(testOrder));
        question.setQuestion(String.format("Question %d", testOrder));
        question.setTestOrder(1);
        
        return question;
    }
    
    public static AnswerEntity createAnswerEntity(int id, String testChar, boolean introvert, QuestionEntity question) {
   	 
	   	 AnswerEntity answer = new AnswerEntity();
	   	 answer.setId(Integer.toUnsignedLong(id));
	   	 answer.setAnswer(String.format("Answer %d", id));
	   	 answer.setIntrovert(introvert);
	   	 answer.setTestChar(testChar);
	   	 answer.setQuestion(question);
	   	 
	   	 return answer;
    }
    
    public static List<AnswerEntity> getAnswersList(int length){

	   	 List<AnswerEntity> answers = new ArrayList<>();
	   	 
	   	 for(int i = 1; i <=length; i++) {
	   		 
	            QuestionEntity q = createQuestionEntity(i);
	
	   		 AnswerEntity a1 = createAnswerEntity(i, "A", false, q);
	   		 answers.add(a1);
	   		 
	   		 AnswerEntity a2 = createAnswerEntity(i, "B", true, q);
	   		 answers.add(a2);
	   	 }
	   	 
	   	 return answers;
    }
}
