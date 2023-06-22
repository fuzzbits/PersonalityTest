package com.afranco.personalitest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.afranco.personalitest.model.AnswerData;
import com.afranco.personalitest.model.QuestionData;
import com.afranco.personalitest.model.entity.AnswerEntity;
import com.afranco.personalitest.model.entity.QuestionEntity;
import com.afranco.personalitest.repository.AnswerRepository;
import com.afranco.personalitest.repository.QuestionRepository;
import com.afranco.personalitest.util.MapperUtil;
import com.afranco.personalitest.util.TestDataUtil;

@ExtendWith(MockitoExtension.class)
public class PersonalitestServiceTest {

	 @Mock
     private QuestionRepository questionRepository;
	 
	 @Mock
	 private AnswerRepository answerRepository;
	 

	 @InjectMocks
     private DefaultPersonalitestService personalitestService;
	 
	 private final long TOTAL_QUESTIONS = 21;
	 
     @Test
     void shouldHandleTestWithNullAnswer() {
    	 
         QuestionEntity question = TestDataUtil.createQuestionEntity(1);
         List<AnswerEntity> answers = List.of(
        		 TestDataUtil.createAnswerEntity(1, "A", true, question),
        		 TestDataUtil.createAnswerEntity(2, "B", false, question)
		 );
         
 		 when(questionRepository.count()).thenReturn(TOTAL_QUESTIONS);
         when(questionRepository.getQuestionByTestOrder(1)).thenReturn(question);
         when(answerRepository.getAnswersByQuestionId(question.getId())).thenReturn(answers);
         
         Map<String,Object> serviceResponse = personalitestService.handleTest(null);
         
         assertEquals(1, serviceResponse.size());
         assertNotNull(serviceResponse.get("question"));
         
         QuestionData serviceQuestion = (QuestionData) serviceResponse.get("question");
         
         compareQuestionsData(serviceQuestion, MapperUtil.mapQuestionData(question, answers));
     }
     
     @Test
     void shouldHandleTestWithValidAnswer() {
    	 
         QuestionEntity question = TestDataUtil.createQuestionEntity(3);
         List<AnswerEntity> answers = List.of(
        		 TestDataUtil.createAnswerEntity(1, "A", true, question),
        		 TestDataUtil.createAnswerEntity(2, "B", false, question)
		 );

 		 when(questionRepository.count()).thenReturn(TOTAL_QUESTIONS);
         when(questionRepository.getQuestionByTestOrder(3)).thenReturn(question);
         when(answerRepository.getAnswersByQuestionId(question.getId())).thenReturn(answers);
         
         Map<String,Object> serviceResponse = personalitestService.handleTest("AB");
         
         assertEquals(1, serviceResponse.size());
         assertNotNull(serviceResponse.get("question"));
         
         QuestionData serviceQuestion = (QuestionData) serviceResponse.get("question");
         
         compareQuestionsData(serviceQuestion, MapperUtil.mapQuestionData(question, answers));
     }
     
     @Test
     void shouldHandleTestWithInvalidAnswer() {
         
    	 when(questionRepository.count()).thenReturn(TOTAL_QUESTIONS);
    	 Map<String,Object> serviceResponse = personalitestService.handleTest("ABABABABABABABABABABABAB");
 		
         assertEquals(0, serviceResponse.size());
     }
     
     @Test
     void shouldHandleTestWithFinalAnswer() {
         
		 List<AnswerEntity> answers = TestDataUtil.getAnswersList((int)TOTAL_QUESTIONS);
		 when(answerRepository.findAll()).thenReturn(answers);
		 when(questionRepository.count()).thenReturn(TOTAL_QUESTIONS);
		 
		 Map<String,Object> serviceResponse = personalitestService.handleTest("ABABABABABABABABABABA");
         
         assertEquals(1, serviceResponse.size());
         assertNotNull(serviceResponse.get("result"));
         
         String serviceResult = (String) serviceResponse.get("result");
         
         assertEquals(serviceResult, "52.38% Extrovert!");
     }
     
     
     public void compareQuestionsData(QuestionData serviceData, QuestionData mockData) {
    	 
    	 assertEquals(serviceData.getQuestion(), mockData.getQuestion());
    	 assertEquals(serviceData.getTestOrder(), mockData.getTestOrder());
    	 
    	 List<AnswerData> serviceList = serviceData.getAnswers();
    	 List<AnswerData> mockList = mockData.getAnswers();
    	 
    	 assertEquals(serviceList.size(), mockList.size());
    	 
    	 for(int i = 0; i < serviceList.size(); i++) {
    		 compareAnswersData(serviceList.get(0), mockList.get(0));
    	 }
     }
     
     private void compareAnswersData(AnswerData serviceData, AnswerData mockData) {
    	 assertEquals(serviceData.getUniqueId(), mockData.getUniqueId());
    	 assertEquals(serviceData.getAnswer(), mockData.getAnswer());
    	 assertEquals(serviceData.isIntrovert(), mockData.isIntrovert());
    	 assertEquals(serviceData.getTestChar(), mockData.getTestChar());
     }

}
