package org.dimensinfin.printer3d.backend.production.request.converter;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

@Component
public class RequestToRequestEntityConverter implements Converter<Request, RequestEntity> {

	@Override
	public RequestEntity convert( final Request input ) {
		Instant requestDate = Instant.now();
		if (null != input.getRequestDate())
			requestDate = Instant.parse( input.getRequestDate());
		LogWrapper.info( "> request data at entity " + requestDate.toString( ) );
		return new RequestEntity.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate( requestDate )
				.withState( input.getState() )
				.withPartList( input.getPartList() )
				.build();
	}
}
