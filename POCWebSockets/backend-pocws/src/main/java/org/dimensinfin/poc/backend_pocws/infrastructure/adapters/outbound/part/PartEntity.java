package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part;


import java.io.Serializable;

import lombok.Getter;

@Getter
public class PartEntity implements Serializable {
	private static final long serialVersionUID = -8299177715885544172L;
	private String id;
	private String name;
	private Integer weight;
}