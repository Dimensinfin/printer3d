package org.dimensinfin.printer3d.backend.production.request.converter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

public class RequestEntityToRequestConverter implements Converter<RequestEntity, Request> {

	@Override
	public Request convert( final RequestEntity input ) {
		return new Request.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate( input.getRequestDate() )
				.withState( input.getState() )
				.withPartList( input.getPartList() )
				.build();
	}
}
