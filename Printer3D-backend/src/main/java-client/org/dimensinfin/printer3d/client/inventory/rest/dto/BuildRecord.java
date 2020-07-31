package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.logging.LogWrapper;

public class BuildRecord {
	private BuildState state = BuildState.IDLE;
	private Part part;
	private int partCopies = 1;
	private Integer buildTime = 0;
	private Instant jobInstallmentDate;

	// - C O N S T R U C T O R S
	private BuildRecord() {}

	// - G E T T E R S   &   S E T T E R S
	public int getJobBuildTime() {
		return this.buildTime;
	}

	public Instant getJobInstallmentDate() {
		return this.jobInstallmentDate;
	}

	public Part getPart() {
		return this.part;
	}

	public int getPartCopies() {
		return this.partCopies;
	}

	/**
	 * Calculates the number of seconds remaining to complete the job by subtracting to the installment datetime the current time and converting
	 * the stored build time expressed in minutes to seconds before the calculation.
	 * @return the number of seconds remaining to complete the build job.
	 */
	public int getRemainingTime() {
		LogWrapper.info( this.toString() );
		if (null == this.jobInstallmentDate) return 0;
		else {
			final long remainingTime = (60 * this.partCopies * this.getJobBuildTime()) - this.getJobInstallmentDate().until( Instant.now(),
					ChronoUnit.SECONDS );
			return (remainingTime < 0) ? 0 : (int) remainingTime;
		}
	}

	public BuildState getState() {
		return this.state;
	}

	public boolean isRunning() {
		return (this.state == BuildState.RUNNING);
	}

	public void clearJob() {
		this.state = BuildState.IDLE;
		this.part = null;
		this.jobInstallmentDate = null;
		this.partCopies = 1;
		this.buildTime = 0;
	}

	// - C O R E
	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "state", this.state )
				.append( "part", this.part )
				.append( "partCopies", this.partCopies )
				.append( "buildTime", this.buildTime )
				.append( "jobInstallmentDate", (null == this.jobInstallmentDate) ? "null" : this.jobInstallmentDate.toString() )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final BuildRecord onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new BuildRecord();
		}

		public BuildRecord build() {
			// Validate the installment date and the part. Both should be null.
			if (null == this.onConstruction.part) {
				this.onConstruction.jobInstallmentDate = null;
				this.onConstruction.buildTime = 0;
			} else {
				this.onConstruction.state = BuildState.RUNNING;
				Objects.requireNonNull( this.onConstruction.jobInstallmentDate );
				if (0 == this.onConstruction.buildTime) this.onConstruction.buildTime = null;
				Objects.requireNonNull( this.onConstruction.buildTime );
			}
			if (null == this.onConstruction.state) this.onConstruction.state = BuildState.IDLE;
			return this.onConstruction;
		}

		public BuildRecord.Builder withJobInstallmentDate( final Instant installmentDate ) {
			this.onConstruction.jobInstallmentDate = installmentDate;
			return this;
		}

		public BuildRecord.Builder withPart( final Part part ) {
			this.onConstruction.part = part;
			return this;
		}

		public BuildRecord.Builder withPartBuildTime( final Integer buildTime ) {
			if (null != buildTime) this.onConstruction.buildTime = buildTime;
			return this;
		}

		public BuildRecord.Builder withPartCopies( final Integer copies ) {
			if (null != copies) this.onConstruction.partCopies = copies;
			return this;
		}
	}
}
