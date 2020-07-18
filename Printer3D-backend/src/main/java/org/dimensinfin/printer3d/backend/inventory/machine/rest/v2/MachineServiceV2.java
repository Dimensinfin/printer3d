package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineUpdaterV2;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineV2Converter;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.v1.MachineServiceV1;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.v1.PartServiceV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

@Service
@Transactional
public class MachineServiceV2 {
	public static DimensinfinError MISSING_MATERIAL_TO_COMPLETE_JOB() {
		return new DimensinfinError.Builder()
				.withErrorName( "MISSING_MATERIAL_TO_COMPLETE_JOB" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".logic.exception" )
				.withHttpStatus( HttpStatus.PRECONDITION_FAILED )
				.withMessage( "Not enough Material or no coil available to start the job." )
				.build();
	}

	public static <T> Collector<T, ?, T> toSingleton() {
		return Collectors.collectingAndThen(
				Collectors.toList(),
				list -> {
					if (list.isEmpty()) throw new DimensinfinRuntimeException( MISSING_MATERIAL_TO_COMPLETE_JOB(),
							"No enough material or no coil while performing the material use before starting a job." );
					LogWrapper.info( "Located coil: " + list.get( 0 ).toString() );
					return list.get( 0 );
				}
		);
	}

	private final MachineRepository machineRepository;
	private final PartRepository partRepository;
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV2( final @NotNull MachineRepository machineRepository,
	                         final @NotNull PartRepository partRepository,
	                         final @NotNull CoilRepository coilRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
		this.coilRepository = coilRepository;
	}

	// - G E T T E R S   &   S E T T E R S
	public MachineListV2 getMachines() {
		final List<MachineV2> machines = this.machineRepository.findAll()
				.stream()
				.map( machineEntity -> {
					// Convert the entity to the api domain
					final BuildRecord buildRecord = new BuildRecord.Builder()
							.withPartCopies( machineEntity.getCurrentPartInstances() )
							.withPart( this.getBuildPart( machineEntity ) )
							.withJobInstallmentDate( machineEntity.getJobInstallmentDate() )
							.build();
					return new MachineEntityToMachineV2Converter( buildRecord ).convert( machineEntity );
				} )
				.collect( Collectors.toList() );
		return new MachineListV2.Builder().withMachines( machines ).build();
	}

	/**
	 * With this command the **Machine** will be active and building one new instance of the referenced Part model. The front end will not allow
	 * setting new jobs until this ends or it is cancelled.
	 * At this moment the plastic quantity that is required to build the Part is subtracted from a Coil with the same finishing. If there are more
	 * than a single coil with the same finishing one of them is used indistinctly.
	 *
	 * @param machineId  the id of the machine where ot start the job.
	 * @param jobRequest the job information record.
	 */
	public MachineV2 startBuild( final @NotNull UUID machineId, final @NotNull JobRequest jobRequest ) {
		LogWrapper.enter();
		try {
			// Find the machine and update it.
			final MachineEntity machineEntity = this.machineRepository.findById( machineId )
					.orElseThrow( () -> new DimensinfinRuntimeException( MachineServiceV1.MACHINE_NOT_FOUND( machineId ) ) );
			final PartEntity jobPartEntity = this.partRepository.findById( jobRequest.getPartId() )
					.orElseThrow( () -> new DimensinfinRuntimeException( PartServiceV1.PART_NOT_FOUND( machineEntity.getCurrentJobPartId() ) ) );
			this.subtractPlasticFromCoil( jobPartEntity, jobRequest.getCopies() );
			final MachineEntity updatedMachineEntity = this.machineRepository.save( new MachineUpdaterV2( machineEntity ).update( jobRequest ) );
			final BuildRecord buildRecord = new BuildRecord.Builder()
					.withPartCopies( updatedMachineEntity.getCurrentPartInstances() )
					.withPart( this.getBuildPart( updatedMachineEntity ) )
					.withJobInstallmentDate( updatedMachineEntity.getJobInstallmentDate() )
					.build();
			return new MachineEntityToMachineV2Converter( buildRecord ).convert( updatedMachineEntity );
		} finally {
			LogWrapper.exit();
		}
	}

	private Part getBuildPart( final MachineEntity machineEntity ) {
		// Check for completed jobs.
		if (null != machineEntity.getCurrentJobPartId()) { // Check if the job has completed
			final Optional<PartEntity> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
			if (jobPartOpt.isEmpty())
				throw new RepositoryConflictException( PartServiceV1.PART_NOT_FOUND( machineEntity.getCurrentJobPartId() ) );
			return new PartEntityToPartConverter().convert( jobPartOpt.get() );
		} else return null;
	}

	/**
	 * Search for a coil with th same finishing than the part on the build job. There can be a single coil or multiple coils. Using streams filter
	 * out all the matching coils and then select the first that has at least double weight left.
	 *
	 * @param partEntity the Part to be build
	 * @param copies     the number of copies to build on this job.
	 */
	private void subtractPlasticFromCoil( final PartEntity partEntity, final int copies ) {
		this.coilRepository.save(
				this.coilRepository.findAll()
						.stream()
						.filter( coilEntity -> partEntity.getMaterial().equals( coilEntity.getMaterial() ) )
						.filter( coilEntity -> partEntity.getColor().equals( coilEntity.getColor() ) )
						.filter( coilEntity -> coilEntity.getWeight() > 2 * partEntity.getWeight() * copies )
						.collect( toSingleton() ) // Gt the first coil on the list or fire an exception if there is no material left on inventory.
						.subtractMaterial( partEntity.getWeight() * copies )
		);
	}
}
