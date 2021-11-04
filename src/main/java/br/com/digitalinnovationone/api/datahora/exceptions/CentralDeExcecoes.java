package br.com.digitalinnovationone.api.datahora.exceptions;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.zone.ZoneRulesException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestControllerAdvice
public class CentralDeExcecoes {
	
	@SuppressWarnings("unused")
	private Erro buildErro(RuntimeException e) {
		return Erro.builder().build()
				.withDataHoraErro(LocalDateTime.now())
				.withMensagem(e.getMessage());	
		
	}
	@ExceptionHandler(ZoneRulesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Erro handleZoneRulesException(ZoneRulesException e) {
		return buildErro(new RuntimeException("Por favor, forneça um timezone válido"));
	}
	
	@ExceptionHandler(DateTimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Erro handleDateTimeException(DateTimeException e) {
		return buildErro(new RuntimeException("Timezone com formato inválido"));
	}

}

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Erro{
	
	private LocalDateTime dataHoraErro;
	private String message;
	
	public Erro withDataHoraErro(final LocalDateTime dataHoraErro) {
        this.dataHoraErro = dataHoraErro;
        return this;
    }

    public Erro withMensagem(final String message) {
        this.message = message;
        return this;
    }
	
}
