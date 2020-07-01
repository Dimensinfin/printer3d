package org.dimensinfin.printer3d.backend.production.request.converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

public class RequestV2ToRequestEntityV2Converter implements Converter<RequestV2, RequestEntityV2> {
	@Override
	public RequestEntityV2 convert( final RequestV2 input ) {
		OffsetDateTime requestDate = OffsetDateTime.now();
		if (null != input.getRequestDate())
			requestDate = OffsetDateTime.parse( input.getRequestDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME );
		LogWrapperLocal.info( "> request data at entity "+ requestDate.format( DateTimeFormatter.ISO_OFFSET_DATE_TIME ) );
		return new RequestEntityV2.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate( requestDate )
				.withState( input.getState() )
				.withContents( input.getContents() )
				.build();
	}
}
