package org.dimensinfin.printer3d.backend.support.inventory.machine;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

public class CucumberTableToSetupRequestConverter extends CucumberTableConverter<SetupRequest> {
	private static final String SETUPREQUEST_PART_ID = "currentJobPart";
	private static final String SETUPREQUEST_JOB_INSTALLMENT_DATE = "jobInstallmentDate";
	private static final String SETUPREQUEST_PART_INSTANCES_COUNT = "partInstancesCount";
	private final String machineLabel;

	// - C O N S T R U C T O R S
	public CucumberTableToSetupRequestConverter( final String machineLabel ) {
		this.machineLabel = Objects.requireNonNull( machineLabel );
	}

	@Override
	public SetupRequest convert( final Map<String, String> cucumberRow ) {
		SetupRequest.Builder builder = new SetupRequest.Builder().withMachineLabel( this.machineLabel );
		if (null != cucumberRow.get( SETUPREQUEST_PART_ID ))
			builder = builder.withPartId( UUID.fromString( cucumberRow.get( SETUPREQUEST_PART_ID ) ) );
		if (null != cucumberRow.get( SETUPREQUEST_JOB_INSTALLMENT_DATE ))
			builder = builder.withPartJobInstallmentDate( this.dynamicDateConversion( cucumberRow.get( SETUPREQUEST_JOB_INSTALLMENT_DATE ) ) );
		if (null != cucumberRow.get( SETUPREQUEST_PART_INSTANCES_COUNT ))
			builder = builder.withPartInstancesCount( Integer.parseInt( cucumberRow.get( SETUPREQUEST_PART_INSTANCES_COUNT ) ) );
		return builder.build();
	}

	protected String dynamicDateConversion( final String reference ) {
		if (reference.equalsIgnoreCase( "<today>" ))
			return OffsetDateTime.now().format( DateTimeFormatter.ISO_OFFSET_DATE_TIME );
		if (reference.equalsIgnoreCase( "<yesterday>" ))
			return OffsetDateTime.now().minus( Period.ofDays( 1 ) ).format( DateTimeFormatter.ISO_OFFSET_DATE_TIME );
		return reference;
	}
}
