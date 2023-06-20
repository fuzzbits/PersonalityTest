package com.afranco.personalitest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;

import com.afranco.personalitest.model.QuestionData;
import com.afranco.personalitest.model.entity.AnswerEntity;
import com.afranco.personalitest.model.entity.QuestionEntity;
import com.afranco.personalitest.repository.AnswerRepository;
import com.afranco.personalitest.repository.QuestionRepository;
import com.afranco.personalitest.util.TestDataUtil;

@ExtendWith(MockitoExtension.class)
public class PersonalitestServiceTest {

	 @Mock
     private QuestionRepository questionRepository;
	 
	 @Mock
	 private AnswerRepository answerRepository;
	 
	 @Spy
	 private Model model;

	 @InjectMocks
     private DefaultPersonalitestService personalitestService;
	 
	 private final int TOTAL_QUESTIONS = 21;
     
     @Test
     void shouldHandleTestWithNullAnswer() {
    	 
         QuestionEntity question = TestDataUtil.createQuestionEntity(1);
         List<AnswerEntity> answers = List.of(
        		 TestDataUtil.createAnswerEntity(1, "A", true, question),
        		 TestDataUtil.createAnswerEntity(2, "B", false, question)
		 );
         
		 ReflectionTestUtils.setField(personalitestService, "TOTAL_QUESTIONS", TOTAL_QUESTIONS);

         when(questionRepository.getQuestionByTestOrder(1)).thenReturn(question);
         when(answerRepository.getAnswersByQuestionId(question.getId())).thenReturn(answers);
         
         String response = personalitestService.handleTest(null, model);
         
         verify(model, times(1)).addAttribute(eq("question"),any(QuestionData.class));
         assertEquals("personalitest",response);
     }
     
     @Test
     void shouldHandleTestWithValidAnswer() {
    	 
         QuestionEntity question = TestDataUtil.createQuestionEntity(3);
         List<AnswerEntity> answers = List.of(
        		 TestDataUtil.createAnswerEntity(1, "A", true, question),
        		 TestDataUtil.createAnswerEntity(2, "B", false, question)
		 );
         
		 ReflectionTestUtils.setField(personalitestService, "TOTAL_QUESTIONS", TOTAL_QUESTIONS);

         when(questionRepository.getQuestionByTestOrder(3)).thenReturn(question);
         when(answerRepository.getAnswersByQuestionId(question.getId())).thenReturn(answers);
         
         String response = personalitestService.handleTest("AB", model);
         
         assertEquals("personalitest",response);
         verify(model, times(1)).addAttribute(eq("question"),any(QuestionData.class));
     }
     
     @Test
     void shouldHandleTestWithInvalidAnswer() {
    	 
		 ReflectionTestUtils.setField(personalitestService, "TOTAL_QUESTIONS", TOTAL_QUESTIONS);
         
         String response = personalitestService.handleTest("ABABABABABABABABABABABAB", model);
         
         assertEquals("error",response);
         verify(model, times(0)).addAttribute(eq("question"),any(QuestionData.class));
     }
     
     @Test
     void shouldHandleTestWithFinalAnswer() {
    	 
		 ReflectionTestUtils.setField(personalitestService, "TOTAL_QUESTIONS", TOTAL_QUESTIONS);
         
		 List<AnswerEntity> answers = TestDataUtil.getAnswersList(TOTAL_QUESTIONS);
		 when(answerRepository.findAll()).thenReturn(answers);
		 
         String response = personalitestService.handleTest("ABABABABABABABABABABA", model);
         
         assertEquals("end_test",response);
         verify(model, times(1)).addAttribute(eq("result"),anyString());
     }
    
}
