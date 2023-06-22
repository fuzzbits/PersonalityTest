package com.afranco.personalitest.web;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.afranco.personalitest.model.QuestionData;
import com.afranco.personalitest.model.entity.AnswerEntity;
import com.afranco.personalitest.model.entity.QuestionEntity;
import com.afranco.personalitest.service.DefaultPersonalitestService;
import com.afranco.personalitest.util.MapperUtil;
import com.afranco.personalitest.util.TestDataUtil;

@WebMvcTest(PersonalitestRestController.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PersonalitestRestControllerTest {

	@MockBean
	private DefaultPersonalitestService personalitestService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldDisplayHomePageCorrectly() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index")).andReturn();
	}

	@Test
	public void shouldDisplayErrorWithInvalidAnswer() throws Exception {
		
		when(personalitestService.handleTest(eq("ABABABABABABABABABABABA")))
		.thenReturn(new HashMap<String,Object>());
		this.mockMvc.perform(get("/personalitest").param("answers", "ABABABABABABABABABABABA")).andExpect(status().isOk())
				.andExpect(view().name("error")).andReturn();
	}
	
	@Test
	public void shouldDisplayTestWithNullAnswer() throws Exception {
		
		QuestionEntity question = TestDataUtil.createQuestionEntity(1);
        List<AnswerEntity> answers = List.of(
       		 TestDataUtil.createAnswerEntity(1, "A", true, question),
       		 TestDataUtil.createAnswerEntity(2, "B", false, question)
		 );
		
        QuestionData questionData = MapperUtil.mapQuestionData(question, answers);
        Map<String,Object> serviceResult = new HashMap<>();
        serviceResult.put("question", questionData);
        
		when(personalitestService.handleTest(isNull())).thenReturn(serviceResult);
		this.mockMvc.perform(get("/personalitest")).andExpect(status().isOk())
				.andExpect(view().name("personalitest"))
				.andExpect(model().attribute("question", serviceResult.get("question")))
				.andReturn();
	}
	
	@Test
	public void shouldDisplayTestWithValidAnswer() throws Exception {
		
		QuestionEntity question = TestDataUtil.createQuestionEntity(3);
        List<AnswerEntity> answers = List.of(
       		 TestDataUtil.createAnswerEntity(1, "A", true, question),
       		 TestDataUtil.createAnswerEntity(2, "B", false, question)
		 );
		
        QuestionData questionData = MapperUtil.mapQuestionData(question, answers);
        Map<String,Object> serviceResult = new HashMap<>();
        serviceResult.put("question", questionData);
        
		when(personalitestService.handleTest(eq("AB"))).thenReturn(serviceResult);
		this.mockMvc.perform(get("/personalitest").param("answers", "AB"))
				.andExpect(status().isOk())
				.andExpect(view().name("personalitest"))
				.andExpect(model().attribute("question", serviceResult.get("question")))
				.andReturn();
	}
	
	@Test
	public void shouldDisplayEndTestWithFinalAnswer() throws Exception {
		
		
        Map<String,Object> serviceResult = new HashMap<>();
        serviceResult.put("result", "52.38% Extrovert!");
        
		when(personalitestService.handleTest(eq("ABABABABABABABABABABA"))).thenReturn(serviceResult);
		this.mockMvc.perform(get("/personalitest").param("answers", "ABABABABABABABABABABA"))
				.andExpect(status().isOk())
				.andExpect(view().name("end_test"))
				.andExpect(model().attribute("result", serviceResult.get("result")))
				.andReturn();
	}
}
