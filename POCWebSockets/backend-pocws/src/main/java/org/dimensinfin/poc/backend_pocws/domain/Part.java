package org.dimensinfin.poc.backend_pocws.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Part {
	UUID id;
	String name;
	Integer weight;
}
