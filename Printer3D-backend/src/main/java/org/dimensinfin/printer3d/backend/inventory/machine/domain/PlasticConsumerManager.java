package org.dimensinfin.printer3d.backend.inventory.machine.domain;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

@Component
public class PlasticConsumerManager {
	public static DimensinfinError errorMISSINGMATERIALTOCOMPLETEJOB() {
		return new DimensinfinError.Builder()
				.withErrorName( "MISSING_MATERIAL_TO_COMPLETE_JOB" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".logic.exception" )
				.withHttpStatus( HttpStatus.PRECONDITION_FAILED )
				.withMessage( "Not enough Material or no coil available to start the job." )
				.build();
	}
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	public PlasticConsumerManager( final CoilRepository coilRepository ) {this.coilRepository = coilRepository;}

	/**
	 * Search for a coil with th same finishing than the part on the build job. There can be a single coil or multiple coils. Using streams filter
	 * out all the matching coils and then select the first that has at least the weight on material required for the build.
	 *
	 * @param partEntity the Part to be build
	 * @param copies     the number of copies to build on this job.
	 */
	public void subtractPlasticFromCoil( final PartEntity partEntity, final int copies ) {
		this.coilRepository.save(
				this.coilRepository.findAll()
						.stream()
						.filter( coilEntity -> partEntity.getMaterial().equals( coilEntity.getMaterial() ) )
						.filter( coilEntity -> partEntity.getColor().equals( coilEntity.getColor() ) )
						.filter( coilEntity -> coilEntity.getWeight() > partEntity.getWeight() * copies )
						.collect( this.toSingleton() ) // Get the most used coil on the list or fire an exception if there is no material left.
						.subtractMaterial( partEntity.getWeight() * copies )
		);
	}

	protected <T extends Coil> T processCoilList( final List<T> coils ) {
		coils.sort( new CoilWeightComparator() );
		final T target = coils.get( 0 );
		LogWrapper.info( MessageFormat.format( "Located coil: {0}", target ) );
		return target;
	}

	/**
	 * Order the list of possible target coils by weight left. So the material should be used from the most used coil.
	 *
	 * If there are no coils matching the request then raise an exception informing about this.
	 *
	 * @param <T> Coil stream collector
	 * @return the selected coil to be used on the job.
	 */
	protected <T extends Coil> Collector<T, ?, T> toSingleton() {
		return Collectors.collectingAndThen(
				Collectors.toList(), list -> {
					if (list.isEmpty()) throw new DimensinfinRuntimeException( errorMISSINGMATERIALTOCOMPLETEJOB(),
							"No enough material or no coil while performing the material use before starting a job." );
					return this.processCoilList( list );
				} );
	}
}
