package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.v1.PartServiceV1;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobEntity;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

@Service
@Transactional
public class MachineServiceV1 {
	public static DimensinfinError errorMACHINENOTFOUND( final UUID machineId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "MACHINE_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".notfound" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Machine with id [{0}] not found at the repository.", machineId ) )
				.build();
	}

	public static DimensinfinError errorMACHINENOTFOUND( final String machineLabel ) {
		return new DimensinfinError.Builder()
				.withErrorName( "MACHINE_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".notfound" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Machine with label [{0}] not found at the repository.", machineLabel ) )
				.build();
	}

	protected final MachineRepository machineRepository;
	protected final PartRepository partRepository;
	private final JobRepository jobRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV1( final MachineRepository machineRepository,
	                         final PartRepository partRepository,
	                         final JobRepository jobRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
		this.jobRepository = Objects.requireNonNull( jobRepository );
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * With this command the **Machine** will be recorded as ready. This cancellation command has not to be related toa real cancellation at the
	 * real 3D printer. Maybe used to correct the current job being performed by the real printer.
	 *
	 * @param machineId the machine where to cancel the running job.
	 */
	public Machine cancelBuild( final @NotNull UUID machineId ) {
		final Optional<MachineEntity> machineOpt = this.machineRepository.findById( machineId );
		if (machineOpt.isEmpty())
			throw new DimensinfinRuntimeException( errorMACHINENOTFOUND( machineId ) );
		return new MachineEntityToMachineConverter( null ).convert( this.machineRepository.save( machineOpt.get().clearJob() ) );
	}

	/**
	 * With this command the **Machine** will complete the current job. This will change the Part stock levels adding to the current Part stock
	 * the number of Parts already built. Then the Machine will return to the IDLE state.
	 * The Job registered on the Machine is then persisted to a nee backend repository to create an analysis record to be able to perform
	 * production statistics with the completed jobs.
	 *
	 * @param machineId the machine identifier that has the job to complete.
	 */
	public Machine completeBuild( final UUID machineId ) {
		final MachineEntity machineEntity = this.machineRepository.findById( machineId )
				.orElseThrow( () -> new DimensinfinRuntimeException( errorMACHINENOTFOUND( machineId ) ) );
		this.storeBuiltParts( machineEntity.getCurrentJobPartId(), machineEntity.getCurrentPartInstances() );
		this.recordJob( machineEntity );
		return new MachineEntityToMachineConverter( null ).convert( this.machineRepository.save( machineEntity.clearJob() ) );
	}

	private void recordJob( final MachineEntity machineEntity ) {
		final PartEntity jobPartEntity = this.partRepository.findById( machineEntity.getCurrentJobPartId() )
				.orElseThrow(()->new DimensinfinRuntimeException( PartServiceV1.errorPARTNOTFOUND( machineEntity.getCurrentJobPartId() ) ));
		final JobEntity job = new JobEntity.Builder()
				.withPartId( machineEntity.getCurrentJobPartId() )
				.withBuildTime( jobPartEntity.getBuildTime() * machineEntity.getCurrentPartInstances() ) // Multiple copies take longer to build.
				.withCost( jobPartEntity.getCost() )
				.withPrice( jobPartEntity.getPrice() )
				.withPartCopies( machineEntity.getCurrentPartInstances() )
				.withJobInstallmentDate( machineEntity.getJobInstallmentDate() )
				.withJobBuildDate( Instant.now() )
				.build();
		this.jobRepository.save( job );
	}

	private void storeBuiltParts( final UUID currentJobPartId, final int currentPartInstances ) {
		this.partRepository.save( this.partRepository.findById( currentJobPartId )
				.orElseThrow( () -> new DimensinfinRuntimeException( PartServiceV1.errorPARTNOTFOUND( currentJobPartId ) ) )
				.incrementStock( currentPartInstances )
		);
	}
}
