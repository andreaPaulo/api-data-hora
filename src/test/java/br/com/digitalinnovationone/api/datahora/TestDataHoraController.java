package br.com.digitalinnovationone.api.datahora;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.cert.CertPathBuilderResult;
import java.time.zone.ZoneRulesException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.restassured.RestAssured;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDataHoraController {
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	void setup() {
		RestAssured.port = port;		
	}
	
	@Test
	void deveRetornarDataHoraComTimezoneDefault(){
		var methodo = "GET";
		var endpoint = "/data-hora";
		var response = when()
				.request(methodo, endpoint)
				.then()
				.extract()
				.response();
		
		var json = response.jsonPath();
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertNotNull(json.get("datHora"));			
		
	}
	@Test
	void deveLancarExceptionTimezoneInvalid(){
		var methodo = "GET";
		var endpoint = "/data-hora?timezone=teste";
		var response = when()
				.request(methodo, endpoint)
				.then()
				.extract()
				.response();
		
		var json = response.jsonPath();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertNotNull(json.get("message"));			
	}
	
	

}
