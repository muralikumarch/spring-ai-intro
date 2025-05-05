package dev.murali.springaiintro.services;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.murali.springaiintro.model.Answer;
import dev.murali.springaiintro.model.GetCapitalRequest;
import dev.murali.springaiintro.model.GetCapitalResponse;
import dev.murali.springaiintro.model.GetCapitalWithInfoResponse;
import dev.murali.springaiintro.model.Question;

@Service
public class OpenAIServiceImpl implements OpenAIService {

	private final ChatClient chatClient;

	public OpenAIServiceImpl(ChatClient.Builder chatClient) {
		this.chatClient = chatClient.build();
	}

	@Override
	public Answer getAnswer(Question question) {
		System.out.println("From the Ask request: ");
		PromptTemplate promptTemplate = new PromptTemplate(question.question());
		Prompt prompt = promptTemplate.create();
		ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
		if (chatResponse == null || chatResponse.getResult() == null || chatResponse.getResult().getOutput() == null) {
			throw new RuntimeException("Chat response or its components are null");
		}
		return new Answer(chatResponse.getResult().getOutput().getText());
	}

	@Override
	public String getAnswer(String question) {
		PromptTemplate promptTemplate = new PromptTemplate(question);
		Prompt prompt = promptTemplate.create();
		ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
		return chatResponse.getResult().getOutput().getText();
	}

	@Value("classpath:templates/get-capital-prompt.st")
	private Resource getCapitalPrompt;

	@Override
	public Answer getCapital(GetCapitalRequest getCapitalRequest) {
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
		ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
		return new Answer(chatResponse.getResult().getOutput().getText());
	}

	@Value("classpath:templates/get-capital-prompt-json.st")
	private Resource getCapitalPromptWithJson;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Answer getCapitalAsJson(GetCapitalRequest getCapitalRequest) {
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithJson);
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
		ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();

		String responseString = chatResponse.getResult().getOutput().getText();
		System.out.println(responseString);

		String jsonResponse = null;
		try {
			JsonNode jsonNode = objectMapper.readTree(responseString);
			jsonResponse = jsonNode.get("answer").asText();
		} catch (JsonProcessingException jpe) {
			 throw new RuntimeException(jpe);
		}
		return new Answer(jsonResponse);
	}

	@Value("classpath:templates/get-capital-prompt-format.st")
	private Resource getCapitalPromptFormat;
	
	@Override
	public GetCapitalResponse getCapitalWithSchema(GetCapitalRequest getCapitalRequest) {
		BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
		String format = converter.getFormat();
		System.out.println(format);
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptFormat);
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
		ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
		
		return converter.convert(chatResponse.getResult().getOutput().getText());
	}

	 
	
	@Override
	public GetCapitalWithInfoResponse getCapitalWithInfoResponse(GetCapitalRequest getCapitalRequest) {
		BeanOutputConverter<GetCapitalWithInfoResponse> converter = new BeanOutputConverter<>(GetCapitalWithInfoResponse.class);
		String format = converter.getFormat();
		System.out.println(format);
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptFormat);
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
		ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
		
		return converter.convert(chatResponse.getResult().getOutput().getText());
	}

}
