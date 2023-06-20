package com.afranco.personalitest.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.afranco.personalitest.PersonalitestApplication;
import com.afranco.personalitest.repository.AnswerRepository;
import com.afranco.personalitest.repository.QuestionRepository;
import com.afranco.personalitest.service.DefaultPersonalitestService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = PersonalitestApplication.class)
public class PersonalitestRestControllerTest {

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private AnswerRepository answerRepository;

	@InjectMocks
	private DefaultPersonalitestService personalitestService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private final int TOTAL_QUESTIONS = 21;

	@Before
	public void setup() {
		
		when(questionRepository.count()).thenReturn(Long.valueOf(21));
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void shouldDisplayHomePageCorrectly() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index")).andReturn();
	}

	@Test
	public void shouldDisplayErrorWithIncorrectAnswer() throws Exception {
		ReflectionTestUtils.setField(personalitestService, "TOTAL_QUESTIONS", TOTAL_QUESTIONS);

		this.mockMvc.perform(get("/personalitest").param("answers", "ABABABABABA")).andExpect(status().isOk())
				.andExpect(view().name("error")).andReturn();
	}
}
