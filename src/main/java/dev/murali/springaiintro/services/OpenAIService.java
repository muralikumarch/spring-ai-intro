package dev.murali.springaiintro.services;

import org.springframework.web.bind.annotation.RequestBody;

import dev.murali.springaiintro.model.Answer;
import dev.murali.springaiintro.model.GetCapitalRequest;
import dev.murali.springaiintro.model.GetCapitalResponse;
import dev.murali.springaiintro.model.GetCapitalWithInfoResponse;
import dev.murali.springaiintro.model.Question;

public interface OpenAIService {

	String getAnswer(String question);
	
	Answer getAnswer(Question question);
	
	Answer getCapital(GetCapitalRequest getCapitalRequest);
	
	Answer getCapitalAsJson(GetCapitalRequest getCapitalRequest);
	
	GetCapitalResponse getCapitalWithSchema(GetCapitalRequest getCapitalRequest);
	
	GetCapitalWithInfoResponse getCapitalWithInfoResponse(GetCapitalRequest getCapitalRequest);
}
