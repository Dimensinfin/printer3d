package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.time.Instant;
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

import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

/**
 * This is an entity used by the customers to report the items they want to buy. The **Request** contains the list of
 * specific Part models along with the material and color that are required by the customer. Also Requests can contain Models that are packaged
 * sets of Parts that are sell together. Requests are processed by order of arrival when the list of current open requests is solicited by the
 * frontend. The system will follow the first come/first served pattern. Then Part build jobs generated by Requests have a higher priority than
 * other jobs generated by inventory leveling.
 * A **Request** can be on different states. It starts on the state **OPEN** when the request is created. When the frontend requests the list of
 * open
 * buy requests then it is compared against the current stock levels. If all the items on the request are available then it moves to the state
 * **COMPLETED**. If the user at the frontend delivers the request to the customer it can then close is and remove the items from the inventory. At
 * this point the request moved to the final state of **CLOSED**.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
@Deprecated
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
	@Column(name = "request_date", columnDefinition = "TIMESTAMP", nullable = false)
	private Instant requestDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "state", columnDefinition = "request_state", nullable = false)
	@Type(type = "pgsql_enum")
	private RequestState state = RequestState.OPEN;
	@Type(type = "jsonb")
	@Column(name = "part_list", columnDefinition = "jsonb")
	private List<String> partList = new ArrayList<>();

	// - C O N S T R U C T O R S
	private RequestEntity() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public List<String> getPartList() {
		return this.partList;
	}

	public Instant getRequestDate() {
		return this.requestDate;
	}

	public RequestState getState() {
		return this.state;
	}

	public boolean isOpen() {
		return (this.state == RequestState.OPEN);
	}

	public RequestEntity close() {
		this.state = RequestState.CLOSE;
		return this;
	}

	public void signalCompleted() {
		this.state = RequestState.COMPLETED;
	}

	// - B U I L D E R
	public static class Builder {
		private final RequestEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RequestEntity();
		}

		public RequestEntity build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.requestDate );
			Objects.requireNonNull( this.onConstruction.state );
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

		public RequestEntity.Builder withPartList( final List<String> partList ) {
			if (null != partList) this.onConstruction.partList = partList;
			return this;
		}

		public RequestEntity.Builder withRequestDate( final Instant requestDate ) {
			this.onConstruction.requestDate = Objects.requireNonNull( requestDate );
			return this;
		}

		public RequestEntity.Builder withState( final RequestState state ) {
			this.onConstruction.state = Objects.requireNonNull( state );
			return this;
		}
	}
}
