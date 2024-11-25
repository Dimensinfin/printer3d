package org.dimensinfin.poc.acceptance.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.dimensinfin.poc.domain.Coil;

import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ScenarioScope
@Component
public class Printer3DWorld {
	private ResponseEntity<Coil> coilV2ListResponseEntity;
	private HttpStatus httpStatus;
	private RuntimeException applicationException;
}
