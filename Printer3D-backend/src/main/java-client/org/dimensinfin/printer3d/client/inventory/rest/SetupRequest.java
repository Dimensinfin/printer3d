package org.dimensinfin.printer3d.client.inventory.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class SetupRequest {
	private String machineLabel;
	private UUID partId;
	private String jobInstallmentDate;
	private Integer partInstancesCount = 1;

	// - C O N S T R U C T O R S
	private SetupRequest() {}

	public Integer getPartInstancesCount() {
		return this.partInstancesCount;
	}

	// - G E T T E R S   &   S E T T E R S
	public String getJobInstallmentDate() {
		return this.jobInstallmentDate;
	}

	public String getMachineLabel() {
		return this.machineLabel;
	}

	public UUID getPartId() {
		return this.partId;
	}

	// - B U I L D E R
	public static class Builder {
		private SetupRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new SetupRequest();
		}

		public SetupRequest build() {
			return this.onConstruction;
		}

		public SetupRequest.Builder withMachineLabel( final String machineLabel ) {
			this.onConstruction.machineLabel = Objects.requireNonNull( machineLabel );
			return this;
		}

		public SetupRequest.Builder withPartId( final UUID partId ) {
			this.onConstruction.partId = Objects.requireNonNull( partId );
			return this;
		}

		public SetupRequest.Builder withPartInstancesCount( final Integer partInstancesCount ) {
			this.onConstruction.partInstancesCount = Objects.requireNonNull( partInstancesCount );
			return this;
		}

		public SetupRequest.Builder withPartJobInstallmentDate( final String installmentDate ) {
			this.onConstruction.jobInstallmentDate = this.convertToDate( Objects.requireNonNull( installmentDate ) ).toString();
			return this;
		}

		private LocalDateTime convertToDate( final String source ) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" );
			return LocalDateTime.parse( source, formatter );
		}
	}
}
