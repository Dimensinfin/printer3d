package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineUpdaterV2;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineV2Converter;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.v1.PartServiceV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

@Service
@Transactional
public class MachineServiceV2 {
	public static <T> Collector<T, ?, T> toSingleton() {
		return Collectors.collectingAndThen(
				Collectors.toList(),
				list -> {
					if (list.isEmpty()) throw new DimensinfinRuntimeException( Printer3DErrorInfo.EXPECTED_COIL_NOT_FOUND() );
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

	public MachineV2 startBuild( final @NotNull UUID machineId, final @NotNull JobRequest jobRequest ) {
		LogWrapper.enter();
		try {
			// Find the machine and update it.
			final Optional<MachineEntity> machineOpt = this.machineRepository.findById( machineId );
			if (machineOpt.isEmpty())
				throw new RepositoryConflictException( Printer3DErrorInfo.MACHINE_NOT_FOUND( machineId.toString() ) );
			final Optional<PartEntity> jobPartOpt = this.partRepository.findById( jobRequest.getPartId() );
			if (jobPartOpt.isEmpty())
				throw new RepositoryConflictException( PartServiceV1.PART_NOT_FOUND( machineOpt.get().getCurrentJobPartId() ) );
			this.subtractPlasticFromCoil( jobPartOpt.get(), jobRequest.getCopies() );
			final MachineEntity machineEntity = this.machineRepository.save( new MachineUpdaterV2( machineOpt.get() ).update( jobRequest ) );
			final BuildRecord buildRecord = new BuildRecord.Builder()
					.withPartCopies( machineEntity.getCurrentPartInstances() )
					.withPart( this.getBuildPart( machineEntity ) )
					.withJobInstallmentDate( machineEntity.getJobInstallmentDate() )
					.build();
			return new MachineEntityToMachineV2Converter( buildRecord ).convert( machineEntity );
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
