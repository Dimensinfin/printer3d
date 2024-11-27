package org.dimensinfin.poc.poccucumber.acceptance.support;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;

//@Getter
//@Setter
@ScenarioScope
@Component
public class POCWorld {
	//	private ResponseEntity<Coil> coilV2ListResponseEntity;
	private HttpStatus httpStatus;
	private RuntimeException applicationException;
}
