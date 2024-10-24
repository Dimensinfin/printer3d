package org.dimensinfin.printer3d.backend.support.accounting.rest;

import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessageConverter;
import org.dimensinfin.printer3d.client.accounting.rest.AccountingApiV1;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AccountFeignClientV1 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public AccountFeignClientV1( final @NotNull ITargetConfiguration acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<String> downloadClosedRequestsData() throws IOException {
		final String ENDPOINT_MESSAGE = "Request Closed Requests data in CSV format.";
		final Response<String> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( ScalarsConverterFactory.create() )
				.build()
				.create( AccountingApiV1.class )
				.downloadClosedRequestsData()
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
	}

	public ResponseEntity<List<WeekAmount>> getRequestsAmountPerWeek( final Integer weekCount ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the amounts aggregated per week rom Requests.";
		final Response<List<WeekAmount>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( AccountingApiV1.class )
				.getRequestsAmountPerWeek( (null == weekCount) ? 4 : weekCount )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
	}
}
