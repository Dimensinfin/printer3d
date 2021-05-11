package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.machine.domain.PlasticConsumerManager;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineUpdaterV2;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.CommonMachineService;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineV2Converter;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.v1.MachineServiceV1;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.PartRestErrors;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

@Service
@Transactional
public class MachineServiceV2 extends CommonMachineService {
	private final MachineRepository machineRepository;
	private final PlasticConsumerManager plasticConsumerManager;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV2( @NotNull final PartRepository partRepository,
	                         @NotNull final MachineRepository machineRepository,
	                         @NotNull final PlasticConsumerManager plasticConsumerManager ) {
		super( partRepository );
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.plasticConsumerManager = plasticConsumerManager;
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * Gets the list of Machines defined on the system and persisted on the Inventory unit.
	 *
	 * Gets the Machine entity and then searches for the current running job if it exists.
	 * If the <code>getCurrentJobPartId()</code> does not return a Part then the <code>BuildRecord</code> is cleared and then the machine entity
	 * converted to the return record.
	 *
	 * Parts can change while the Part is being used on the job and this can affect the job build time. So the code should isolate from this change
	 * by storing the build time onto the Machine BuildRecord for later retrieval so this data is not affected by Part changes.
	 */
	public List<MachineV2> getMachines() {
		return this.machineRepository.findAll()
				.stream()
				.map( machineEntity -> {
					// Convert the entity to the api domain
					try {
						final BuildRecord buildRecord = new BuildRecord.Builder()
								.withPartCopies( machineEntity.getCurrentPartInstances() )
								.withPartBuildTime( machineEntity.getCurrentJobPartBuildTime() )
								.withPart( this.getBuildPart( machineEntity ) ) // Search for the Part instance at the repository.
								.withJobInstallmentDate( machineEntity.getJobInstallmentDate() )
								.build();
						return new MachineEntityToMachineV2Converter( buildRecord ).convert( machineEntity );
					} catch (final NullPointerException npe) {
						LogWrapper.error( npe );
						throw new DimensinfinRuntimeException( DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR(
								"Null pointer found while building a BuildRecord" ) );
					} catch (final RuntimeException rte) {
						LogWrapper.error( rte );
						throw new DimensinfinRuntimeException( DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR(
								rte.getMessage() )
						);
					}
				} )
				.collect( Collectors.toList() );
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
			final var machineEntity = this.machineRepository.findById( machineId )
					.orElseThrow( () -> new DimensinfinRuntimeException( MachineServiceV1.errorMACHINENOTFOUND( machineId ) ) );
			final PartEntity jobPartEntity = this.partRepository.findById( jobRequest.getPartId() )
					.orElseThrow( () -> new DimensinfinRuntimeException( PartRestErrors.errorPARTNOTFOUND( machineEntity.getCurrentJobPartId() ) ) );
			this.plasticConsumerManager.subtractPlasticFromCoil( jobPartEntity, jobRequest.getCopies() );
			final var updatedMachineEntity = this.machineRepository.save( new MachineUpdaterV2( machineEntity ).update(
					jobRequest,
					jobPartEntity.getBuildTime()
			) );
			final BuildRecord buildRecord = new BuildRecord.Builder()
					.withPartCopies( updatedMachineEntity.getCurrentPartInstances() )
					.withPartBuildTime( jobPartEntity.getBuildTime() )
					.withPart( this.getBuildPart( updatedMachineEntity ) )
					.withJobInstallmentDate( updatedMachineEntity.getJobInstallmentDate() )
					.build();
			return new MachineEntityToMachineV2Converter( buildRecord ).convert( updatedMachineEntity );
		} finally {
			LogWrapper.exit();
		}
	}
}
