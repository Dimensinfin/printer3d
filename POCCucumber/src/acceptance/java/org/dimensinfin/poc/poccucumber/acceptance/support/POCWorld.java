package org.dimensinfin.poc.poccucumber.acceptance.support;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.dimensinfin.poc.poccucumber.domain.Coil;

import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ScenarioScope
@Component
public class POCWorld {
	private List<Coil> coils;
	private HttpStatus httpStatus;
	private RuntimeException applicationException;
	private ResponseEntity<Coil> responseEntityCoil;
}
