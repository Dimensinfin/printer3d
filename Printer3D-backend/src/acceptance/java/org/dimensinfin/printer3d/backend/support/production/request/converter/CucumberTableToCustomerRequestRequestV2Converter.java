package org.dimensinfin.printer3d.backend.support.production.request.converter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.CUSTOMER;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PAID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.TOTAL;

public class CucumberTableToCustomerRequestRequestV2Converter extends CucumberTableConverter<CustomerRequestRequestV2> {
	private final List<RequestItem> requestContents;

	// - C O N S T R U C T O R S
	public CucumberTableToCustomerRequestRequestV2Converter( final List<RequestItem> requestContents ) {this.requestContents = requestContents;}

	@Override
	public CustomerRequestRequestV2 convert( final Map<String, String> cucumberRow ) {
		final CustomerRequestRequestV2.Builder builder = new CustomerRequestRequestV2.Builder();
		if ( null != cucumberRow.get( ID ) ) builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if ( null != cucumberRow.get( LABEL ) ) builder.withLabel( cucumberRow.get( LABEL ) );
		if ( null != cucumberRow.get( REQUEST_DATE ) ) builder.withRequestDate( this.dynamicDateConversion( cucumberRow.get( REQUEST_DATE ) ) );
		if ( null != cucumberRow.get( PAID ) ) builder.withPaid( Boolean.parseBoolean( cucumberRow.get( PAID ) ) );
		if ( Objects.nonNull( cucumberRow.get( TOTAL ) ) ) builder.withTotalAmount( Float.parseFloat( cucumberRow.get( TOTAL ) ) );
		else builder.withTotalAmount( 0.0F );
		builder.withContents( this.requestContents );
		return builder.build()
				.setCustomer( cucumberRow.get( CUSTOMER ) );
	}
}
