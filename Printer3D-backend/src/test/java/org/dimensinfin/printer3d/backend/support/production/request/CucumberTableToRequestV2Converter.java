package org.dimensinfin.printer3d.backend.support.production.request;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STATE;

public class CucumberTableToRequestV2Converter extends CucumberTableConverter<RequestV2> {
	private final List<RequestItem> requestContents;

	// - C O N S T R U C T O R S
	public CucumberTableToRequestV2Converter( final List<RequestItem> requestContents ) {this.requestContents = requestContents;}

	@Override
	public RequestV2 convert( final Map<String, String> cucumberRow ) {
		final RequestV2.Builder builder = new RequestV2.Builder();
		if (null != cucumberRow.get( ID )) builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( LABEL )) builder.withLabel( cucumberRow.get( LABEL ) );
		if (null != cucumberRow.get( STATE )) builder.withState( RequestState.valueOf( cucumberRow.get( STATE ) ) );
		if (null != cucumberRow.get( REQUEST_DATE )) builder.withRequestDate( cucumberRow.get( REQUEST_DATE ) );
		builder.withContents( this.requestContents );
		return builder.build();
	}
}
