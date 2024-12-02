package org.dimensinfin.poc.generated.infrastructure.ports.inbound.domain;

import java.util.UUID;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

/**
 * PartDto
 */
@Builder
@Getter
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class PartDto   {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("weight")
  private Integer weight;
}

