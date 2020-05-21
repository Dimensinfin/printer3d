package org.dimensinfin.printer3d.backend.part.persistence;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "inventory", schema = "printer3d")
public class PartEntity {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id = null;
	@NotBlank
	@Size(min = 3, max = 50)
	@Column(name = "label", updatable = false, nullable = false)
	private String label = null;
	@Size(max = 500)
	@Column(name = "description", updatable = true, nullable = true)
	private String description = null;
	@Column(name = "cost", updatable = true, nullable = false)
	private Float cost = null;
	@Column(name = "price", updatable = true, nullable = false)
	private Float price = null;
	@Column(name = "active", updatable = true, nullable = false)
	private Boolean active = true;
}
