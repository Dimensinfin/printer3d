package org.dimensinfin.printer3d.backend.support.production.request.rest;

import java.io.IOException;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessageConverter;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestFeignClientV2 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RequestFeignClientV2( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<CustomerRequestResponseV2> closeRequest( final UUID requestId ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to close an specific Request.";
		final Response<CustomerRequestResponseV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV2.class )
				.closeRequest( requestId )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else {
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
		}
	}

	public ResponseEntity<CounterResponse> deleteRequest( final UUID requestId ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to delete from the repository the selected Request.";
		final Response<CounterResponse> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV2.class )
				.deleteRequest( requestId )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else {
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
		}
	}

	public ResponseEntity<CustomerRequestResponseV2> newRequest( final CustomerRequestRequestV2 newRequest ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Request.";
		final Response<CustomerRequestResponseV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV2.class )
				.newRequest( newRequest )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else {
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
		}
	}
}
