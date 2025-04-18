package org.dimensinfin.printer3d.backend.support.production.request;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.backend.support.core.CommonValidator;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

import static io.micrometer.core.instrument.Statistic.TOTAL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.AMOUNT;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.CUSTOMER;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.DELIVERED_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.IVA;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PAID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PAYMENT_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STATE;

public class CustomerRequestResponseV2Validator extends CommonValidator implements Validator<CustomerRequestResponseV2> {

	@Override
	public boolean validate( final Map<String, String> rowData, final CustomerRequestResponseV2 record ) {
		if (null != rowData.get( ID )) Assertions.assertEquals( rowData.get( ID ), record.getId().toString() );
		if (null != rowData.get( LABEL )) Assertions.assertEquals( rowData.get( LABEL ), record.getLabel() );
		if (null != rowData.get( CUSTOMER )) Assertions.assertEquals( rowData.get( CUSTOMER ), record.getCustomer() );
		if (null != rowData.get( REQUEST_DATE )) Assertions.assertEquals(
				Instant.parse( rowData.get( REQUEST_DATE ) ),
				Instant.parse( record.getRequestDate().toString() )
		);
		//		fieldValidation(DELIVERED_DATE)
		if (null != rowData.get( DELIVERED_DATE )) Assertions.assertEquals(
				this.cucumberDataReplacer( rowData.get( DELIVERED_DATE ), record.getDeliveredDate().toString() ),
				record.getDeliveredDate().toString()
		);
		if (null != rowData.get( PAYMENT_DATE )) Assertions.assertEquals(
				this.cucumberDataReplacer( rowData.get( PAYMENT_DATE ), record.getPaymentDate().toString() ),
				record.getPaymentDate().toString()
		);
		if (null != rowData.get( STATE )) Assertions.assertEquals( rowData.get( STATE ), record.getState().name() );
		if (null != rowData.get( PAID )) Assertions.assertEquals( Boolean.parseBoolean( rowData.get( PAID ) ), record.isPaid() );
		if (null != rowData.get( AMOUNT )) Assertions.assertEquals( Float.parseFloat( rowData.get( AMOUNT ) ), record.getAmount(), 0.01 );
		if (null != rowData.get( IVA )) Assertions.assertEquals( Float.parseFloat( rowData.get( IVA ) ), record.getIva(), 0.01 );
		if (null != rowData.get( TOTAL )) Assertions.assertEquals( Float.parseFloat( rowData.get( TOTAL ) ), record.getTotal(), 0.01 );
		return true;
	}
}
