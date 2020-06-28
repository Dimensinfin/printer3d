package org.dimensinfin.printer3d.backend.production.request.converter;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

@Component
public class RequestEntityV2ToRequestV2Converter implements Converter<RequestEntityV2, RequestV2> {

	@Override
	public RequestV2 convert( final RequestEntityV2 input ) {
		return new RequestV2.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate( input.getRequestDate().format( DateTimeFormatter.ISO_OFFSET_DATE_TIME ) )
				.withState( input.getState() )
				.withContents( input.getContents() )
				.build();
	}
}
