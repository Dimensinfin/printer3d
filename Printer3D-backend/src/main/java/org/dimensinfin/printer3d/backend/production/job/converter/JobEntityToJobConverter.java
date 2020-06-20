package org.dimensinfin.printer3d.backend.production.job.converter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.JobEntity;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

public class JobEntityToJobConverter implements Converter<JobEntity, JobHistoric> {

	@Override
	public JobHistoric convert( final JobEntity input ) {
		return new JobHistoric.Builder()
				.withId( input.getId() )
				.withPartId( input.getJobPartId() )
				.withBuildTime( input.getBuildTime() )
				.withCost( input.getCost() )
				.withPrice( input.getPrice() )
				.withPartCopies( input.getPartCopies() )
				.withJobInstallmentDate( input.getJobInstallmentDate() )
				.withJobBuildDate( input.getJobBuildDate() )
				.build();
	}
}
