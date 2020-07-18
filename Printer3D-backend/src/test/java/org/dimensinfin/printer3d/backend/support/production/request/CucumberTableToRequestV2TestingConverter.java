package org.dimensinfin.printer3d.backend.support.production.request;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestV2Testing;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.AMOUNT;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.CLOSED_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STATE;

public class CucumberTableToRequestV2TestingConverter extends CucumberTableConverter<RequestV2Testing> {
	private final List<RequestItem> requestContents;

	// - C O N S T R U C T O R S
	public CucumberTableToRequestV2TestingConverter( final List<RequestItem> requestContents ) {this.requestContents = requestContents;}

	@Override
	public RequestV2Testing convert( final Map<String, String> cucumberRow ) {
		final RequestV2Testing.Builder builder = new RequestV2Testing.Builder();
		if (null != cucumberRow.get( ID )) builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( LABEL )) builder.withLabel( cucumberRow.get( LABEL ) );
		if (null != cucumberRow.get( STATE )) builder.withState( RequestState.valueOf( cucumberRow.get( STATE ) ) );
		if (null != cucumberRow.get( REQUEST_DATE )) builder.withRequestDate( cucumberRow.get( REQUEST_DATE ) );
		if (null != cucumberRow.get( CLOSED_DATE )) builder.withClosedDate( cucumberRow.get( CLOSED_DATE ) );
		if (null != cucumberRow.get( AMOUNT )) builder.withAmount( Float.parseFloat( cucumberRow.get( AMOUNT ) ) );
		builder.withContents( this.requestContents );
		return builder.build();
	}
}
