package org.dimensinfin.poc.poccucumber.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coil implements Serializable {
	private static final long serialVersionUID = 4711864502082811040L;
	@JsonProperty("name")
	private String name;
}
