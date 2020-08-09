package org.dimensinfin.printer3d.backend.production.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.production.job.FinishingByCountComparator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

public class JobSorter {
	public List<Job> sortByFinishingCount( final List<Job> inputList ) {
		final List<FinishingContainer> finishings = this.generateFinishingList( inputList );
		finishings.sort( new FinishingByCountComparator() );
		LogWrapper.info( "Finishings: " + finishings.toString() );
		return finishings
				.stream()
				.flatMap( finishingContainer -> finishingContainer.getJobs().stream() )
				.collect( Collectors.toList() );
	}

	private String generateFinishingKey( final Part target ) {
		return target.getId() + ":" + target.getMaterial() + ":" + target.getColor();
	}

	private List<FinishingContainer> generateFinishingList( final List<Job> inputJobs ) {
		final Map<String, FinishingContainer> finishings = new HashMap<>(); // Initialize the result list
		for (Job job : inputJobs) {
			final String key = this.generateFinishingKey( job.getPart() );
			finishings.computeIfAbsent( key, finishingContainer -> new FinishingContainer.Builder().build() );
			finishings.compute( key, ( String targetKey, FinishingContainer container ) -> Objects.requireNonNull( container ).addJob( job ) );
		}
		LogWrapper.info( "Finishings: " + finishings.values().toString() );
		return new ArrayList<>( finishings.values() );
	}
}
