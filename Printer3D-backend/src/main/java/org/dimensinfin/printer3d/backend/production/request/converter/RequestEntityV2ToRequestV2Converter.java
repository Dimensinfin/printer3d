package org.dimensinfin.printer3d.backend.production.request.converter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;

@Deprecated
@Component
public class RequestEntityV2ToRequestV2Converter implements Converter<RequestEntityV2, CustomerRequestRequestV2> {

	@Override
	public CustomerRequestRequestV2 convert( final RequestEntityV2 input ) {
		return new CustomerRequestRequestV2.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withRequestDate( input.getRequestDate().toString() )
				//				.withClosedDate( (null != input.getPaymentDate()) ? input.getPaymentDate().toString() : null )
				.withState( input.getState() )
				//				.withAmount( input.getAmount() )
				.withContents( input.getContents() )
				.build();
	}
}
