package org.dimensinfin.printer3d.backend.production.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.printer3d.client.production.rest.dto.Job;

public class FinishingContainer {
	private List<Job> jobs = new ArrayList<>();

	// - C O N S T R U C T O R S
	private FinishingContainer() {}

	// - G E T T E R S   &   S E T T E R S
	public int getJobCount() {
		return this.jobs.size();
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public FinishingContainer addJob( final Job job ) {
		this.jobs.add( job );
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this , ToStringStyle.JSON_STYLE)
				.append( "jobs", jobs )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final FinishingContainer onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new FinishingContainer();
		}

		public FinishingContainer build() {
			return this.onConstruction;
		}
	}
}
