package org.dimensinfin.printer3d.backend.support.production.request.rest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessageConverter;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiV3;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestFeignClientV3 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RequestFeignClientV3( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<List<CustomerRequestResponseV2>> getClosedRequests() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of Open v2 requests.";
		final Response<List<CustomerRequestResponseV2>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV3.class )
				.getClosedRequests()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<List<CustomerRequestResponseV2>> getOpenRequests() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of Open v2 requests.";
		final Response<List<CustomerRequestResponseV2>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV3.class )
				.getOpenRequests()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<CustomerRequestResponseV2> deliverRequest( final UUID requestId ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to deliver the selected Request.";
		final Response<CustomerRequestResponseV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV3.class )
				.deliverRequest( requestId )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else {
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
		}
	}
}