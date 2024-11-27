package org.dimensinfin.poc.poccucumber.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coil implements Serializable {
	private static final long serialVersionUID = 4711864502082811040L;
	private String name;
}
