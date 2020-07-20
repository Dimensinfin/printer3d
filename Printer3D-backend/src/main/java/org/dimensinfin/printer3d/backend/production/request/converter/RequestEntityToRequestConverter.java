package org.dimensinfin.printer3d.backend.production.request.converter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

@Component
public class RequestEntityToRequestConverter implements Converter<RequestEntity, Request> {
	@Deprecated
	@Override
	public Request convert( final RequestEntity input ) {
		return new Request.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate( input.getRequestDate().toString() )
				.withState( input.getState() )
				.withPartList( input.getPartList() )
				.build();
	}
}
