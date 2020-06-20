package org.dimensinfin.printer3d.backend.support.production.request.rest;

import java.io.IOException;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiV1;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestFeignClientV1 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RequestFeignClientV1( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<RequestList> getOpenRequests() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of Open repository requests.";
		final Response<RequestList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV1.class )
				.getOpenRequests()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<Request> closeRequest( final UUID requestId ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to close an specific Request.";
		final Response<Request> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV1.class )
				.closeRequest( requestId )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<Request> newRequest( final Request newRequest ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Request.";
		final Response<Request> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV1.class )
				.newRequest( newRequest )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
