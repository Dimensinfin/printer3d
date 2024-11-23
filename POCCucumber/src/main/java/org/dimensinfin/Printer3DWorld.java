package org.dimensinfin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.poc.domain.Coil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Printer3DWorld {
	private ResponseEntity<Coil> coilV2ListResponseEntity;
	private HttpStatus httpStatus;
	private RuntimeException applicationException;
}
