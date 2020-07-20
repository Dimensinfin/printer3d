package org.dimensinfin.printer3d.backend.production.request.converter;

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
				.withRequestDate( input.getRequestDate().toString() )
				.withClosedDate( (null != input.getClosedDate()) ? input.getClosedDate().toString() : null )
				.withState( input.getState() )
				.withAmount( input.getAmount() )
				.withContents( input.getContents() )
				.build();
	}
}
