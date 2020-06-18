package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

@Entity
@Table(name = "requests", schema = "printer3d")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class RequestEntity {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@Size(min = 3, max = 50)
	@Column(name = "label", updatable = false, nullable = false)
	private String label;
	@Column(name = "request_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime requestDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "state", columnDefinition = "request_state", nullable = false)
	@Type(type = "pgsql_enum")
	private RequestState state = RequestState.OPEN;
	@Type(type = "jsonb")
	@Column(name = "part_list", columnDefinition = "jsonb")
	private List<PartRequest> partList = new ArrayList<PartRequest>();

	// - C O N S T R U C T O R S
	private RequestEntity() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public List<PartRequest> getPartList() {
		return this.partList;
	}

	public OffsetDateTime getRequestDate() {
		return this.requestDate;
	}

	public RequestState getState() {
		return this.state;
	}

	public boolean isOpen() {
		return (this.state == RequestState.OPEN);
	}

	public void signalCompleted() {
		this.state = RequestState.COMPLETED;
	}

	// - B U I L D E R
	// - B U I L D E R
	public static class Builder {
		private final RequestEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RequestEntity();
		}

		public RequestEntity build() {
			return this.onConstruction;
		}

		public RequestEntity.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public RequestEntity.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public RequestEntity.Builder
		withPartList( final List<PartRequest> partList ) {
			this.onConstruction.partList = Objects.requireNonNull( partList );
			return this;
		}

		public RequestEntity.Builder withRequestDate( final OffsetDateTime requestDate ) {
			this.onConstruction.requestDate = Objects.requireNonNull( requestDate );
			return this;
		}

		public RequestEntity.Builder withState( final RequestState state ) {
			this.onConstruction.state = Objects.requireNonNull( state );
			return this;
		}
	}
}
