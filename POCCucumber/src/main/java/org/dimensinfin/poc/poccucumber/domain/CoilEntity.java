package org.dimensinfin.poc.poccucumber.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoilEntity implements Serializable {
	private static final long serialVersionUID = 4711864502082811040L;
	private UUID id;
	private String name;
}
