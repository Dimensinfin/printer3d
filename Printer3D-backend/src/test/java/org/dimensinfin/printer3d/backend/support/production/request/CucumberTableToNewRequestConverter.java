package org.dimensinfin.printer3d.backend.support.production.request;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STATE;

public class CucumberTableToNewRequestConverter extends CucumberTableConverter<Request> {
	private final List<PartRequest> partRequestList;

	// - C O N S T R U C T O R S
	public CucumberTableToNewRequestConverter( final @NotNull List<PartRequest> partRequestList ) {
		this.partRequestList = Objects.requireNonNull( partRequestList );
	}

	@Override
	public Request convert( final Map<String, String> cucumberRow ) {
		Request.Builder builder = new Request.Builder();
		if (null != cucumberRow.get( ID )) builder = builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( LABEL )) builder = builder.withLabel( cucumberRow.get( LABEL ) );
		if (null != cucumberRow.get( REQUEST_DATE ))
			builder = builder.withRequestDate(
					this.dynamicDateConversion( cucumberRow.get( REQUEST_DATE ) )
			);
		if (null != cucumberRow.get( STATE )) builder = builder.withState( RequestState.valueOf( cucumberRow.get( STATE ) ) );
		builder = builder.withPartList( this.partRequestList );
		return builder.build();
	}
}
