package org.dimensinfin.printer3d.backend.production.request.converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

public class RequestToRequestEntityConverter implements Converter<Request, RequestEntity> {

	@Override
	public RequestEntity convert( final Request input ) {
		OffsetDateTime requestDate = OffsetDateTime.now();
		if (null != input.getRequestDate())
			requestDate = OffsetDateTime.parse( input.getRequestDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME );
		LogWrapperLocal.info( "> request data at entity", requestDate.format( DateTimeFormatter.ISO_OFFSET_DATE_TIME ) );
		return new RequestEntity.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate( requestDate )
				.withState( input.getState() )
				.withPartList( input.getPartList() )
				.build();
	}
}
