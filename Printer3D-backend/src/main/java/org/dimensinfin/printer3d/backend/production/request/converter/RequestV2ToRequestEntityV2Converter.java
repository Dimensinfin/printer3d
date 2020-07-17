package org.dimensinfin.printer3d.backend.production.request.converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

public class RequestV2ToRequestEntityV2Converter implements Converter<RequestV2, RequestEntityV2> {
	@Override
	public RequestEntityV2 convert( final RequestV2 input ) {
		return new RequestEntityV2.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate(
						(null != input.getRequestDate()) ?
								OffsetDateTime.parse( input.getRequestDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME ) :
								OffsetDateTime.now()
				)
				.withClosedDate(
						(null != input.getClosedDate()) ?
								Instant.parse( input.getClosedDate() ) :
								Instant.now()
				)
				.withState( input.getState() )
				.withAmount( input.getAmount() )
				.withContents( input.getContents() )
				.build();
	}
}
