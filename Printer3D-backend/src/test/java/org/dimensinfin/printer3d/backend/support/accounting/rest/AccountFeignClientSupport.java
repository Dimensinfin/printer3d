package org.dimensinfin.printer3d.backend.support.accounting.rest;

import java.io.IOException;
import java.time.Instant;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessageConverter;
import org.dimensinfin.printer3d.client.accounting.rest.AccountingApiSupport;

import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountFeignClientSupport extends CommonFeignClient {

	// - C O N S T R U C T O R S
	public AccountFeignClientSupport( final @NotNull ITargetConfiguration acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<CounterResponse> moveBackCloseDates( final Instant newCloseDate ) throws IOException {
		final String ENDPOINT_MESSAGE = "Move back all closed request dates to the selected date.";
		final Response<CounterResponse> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( AccountingApiSupport.class )
				.setCloseDate( newCloseDate )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
	}
}
