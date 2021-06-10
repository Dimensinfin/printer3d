package org.dimensinfin.printer3d.backend.production.request.converter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.17.0
 */
public class RequestEntityV2ToCustomerRequestResponseConverter implements Converter<RequestEntityV2, CustomerRequestResponseV2> {

	@Override
	public CustomerRequestResponseV2 convert( final RequestEntityV2 input ) {
		return new CustomerRequestResponseV2()
				.setId( input.getId() )
				.setLabel( input.getLabel() )
				.setContents( input.getContents() )
				.setCustomer( input.getCustomer() )
				.setRequestDate( input.getRequestDate() )
				.setState( input.getState() )
				.setAmount( input.getAmount() )
				.setIva( input.getIva() )
				.setTotal( input.getTotal() )
				.setPaid( input.isPaid() )
				.setDeliveredDate( input.getDeliveredDate() )
				.setPaymentDate( input.getPaymentDate() );
	}
}