package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part;


import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartEntity implements Serializable {
	private static final long serialVersionUID = -8299177715885544172L;
	private UUID id;
	private String name;
	private Integer weight;
}