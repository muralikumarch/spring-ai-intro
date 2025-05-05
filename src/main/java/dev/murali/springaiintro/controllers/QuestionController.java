package dev.murali.springaiintro.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.murali.springaiintro.model.Answer;
import dev.murali.springaiintro.model.GetCapitalRequest;
import dev.murali.springaiintro.model.GetCapitalResponse;
import dev.murali.springaiintro.model.GetCapitalWithInfoResponse;
import dev.murali.springaiintro.model.Question;
import dev.murali.springaiintro.services.OpenAIService;

@RestController
public class QuestionController {

	private final OpenAIService openAIService;

	public QuestionController(OpenAIService openAIService) {
		this.openAIService = openAIService;
	}

	@PostMapping("/ask")
	public Answer askQuestion(@RequestBody Question question) {
		System.out.println("Initilazing the ask...");
		return openAIService.getAnswer(question);
	}

	@PostMapping("/capital")
	public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
		return this.openAIService.getCapital(getCapitalRequest);
	}

	@PostMapping("/capitaljson")
	public Answer getCapitalAsJson(@RequestBody GetCapitalRequest getCapitalRequest) {
		return this.openAIService.getCapitalAsJson(getCapitalRequest);
	}

	@PostMapping("/capitalasformat")
	public GetCapitalResponse getCapitalReqResFormat(@RequestBody GetCapitalRequest getCapitalRequest) {
		return this.openAIService.getCapitalWithSchema(getCapitalRequest);
	}

	@PostMapping("/capitalinfo")
	public GetCapitalWithInfoResponse getCapitalWithInfoResponse(@RequestBody GetCapitalRequest getCapitalRequest) {
		return this.openAIService.getCapitalWithInfoResponse(getCapitalRequest);
	}
}
