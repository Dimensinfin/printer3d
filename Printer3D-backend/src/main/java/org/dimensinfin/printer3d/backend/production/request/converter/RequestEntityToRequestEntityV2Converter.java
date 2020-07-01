package org.dimensinfin.printer3d.backend.production.request.converter;

import java.util.ArrayList;
import java.util.List;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

public class RequestEntityToRequestEntityV2Converter implements Converter<RequestEntity, RequestEntityV2> {

	@Override
	public RequestEntityV2 convert( final RequestEntity input ) {
		LogWrapperLocal.enter();
		try {
			// Extract the Part ids from v1 and rewrap for v2.
			final List<RequestItem> contents = new ArrayList<>();
			for (PartRequest partRequest : input.getPartList())
				contents.add(
						new RequestItem.Builder()
								.withItemId( partRequest.getPartId() )
								.withType( RequestContentType.PART )
								.withQuantity( partRequest.getQuantity() )
								.build()
				);
			return new RequestEntityV2.Builder()
					.withId( input.getId() )
					.withLabel( input.getLabel() )
					.withState( input.getState() )
					.withRequestDate( input.getRequestDate() )
					.withContents( contents )
					.build();
		} finally {
			LogWrapperLocal.exit();
		}
	}
}
