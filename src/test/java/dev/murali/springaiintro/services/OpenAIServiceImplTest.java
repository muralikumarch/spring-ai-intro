package dev.murali.springaiintro.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAIServiceImplTest {
	
	@Autowired
	OpenAIService openAIService;

	 

	@Test
	void testGetAnswer() {
		//fail("Not yet implemented");
		String answer = openAIService.getAnswer("Tell me a famous poem in Telugu");
		System.out.println(answer);  
	}

}
