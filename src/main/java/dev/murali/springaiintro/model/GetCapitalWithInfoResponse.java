package dev.murali.springaiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalWithInfoResponse(@JsonPropertyDescription("This is the city name") String city,
		@JsonPropertyDescription("The population of the city") Integer population,
		@JsonPropertyDescription("The region of city is located in") String region,
		@JsonPropertyDescription("The primary language spoken") String language,
		@JsonPropertyDescription("The currency used") String currency) {

}
