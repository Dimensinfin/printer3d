package org.dimensinfin.printer3d.backend.production.request.converter;

import java.time.Instant;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;

@Deprecated
public class CustomerRequestRequestV2ToRequestEntityV2Converter implements Converter<CustomerRequestRequestV2, RequestEntityV2> {
	@Override
	public RequestEntityV2 convert( final CustomerRequestRequestV2 input ) {
		return new RequestEntityV2.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate(
						(null != input.getRequestDate()) ?
								Instant.parse( input.getRequestDate() ) :
								Instant.now()
				)
				.withState( input.getState() )
				.withTotal( input.getTotal() )
				.withContents( input.getContents() )
				.build();
	}
}
