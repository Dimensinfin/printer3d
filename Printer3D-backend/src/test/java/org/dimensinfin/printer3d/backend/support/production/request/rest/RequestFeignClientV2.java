package org.dimensinfin.printer3d.backend.support.production.request.rest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.exception.DimensinfinErrorInfo;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.common.exception.RestExceptionMessage;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessageConverter;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestFeignClientV2 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RequestFeignClientV2( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<List<RequestV2>> getOpenRequests() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of Open v2 requests.";
		final Response<List<RequestV2>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV2.class )
				.getOpenRequests()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<RequestV2> closeRequest( final UUID requestId ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to close an specific Request.";
		final Response<RequestV2> response = new Retrofit.Builder()
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
//			if (response.code() == 500) {
				final RestExceptionMessage restException = new RestExceptionMessageConverter().convert( response.errorBody().string() );
//				final String message = restException.getMessage();
			throw new DimensinfinRuntimeException( DimensinfinErrorInfo.INVALID_REQUEST_STRUCTURE( restException ));
//				throw new DimensinfinRuntimeException( ErrorInfo.INVALID_REQUEST_STRUCTURE, message );
//			}
//			if (response.code() == 409) {
//				final RestExceptionMessage restException = new RestExceptionMessageConverter().convert( response.errorBody().string() );
//				//				final String message = restException.getMessage();
//				throw new RestExceptionMessageToDimensinfinRuntimeExceptionConverter().convert( restException );
//			}
//			final AppErrorInfo appException = new AppErrorInfoConverter().convert( response.errorBody().string() );
//			throw new AppErrorInfoToDimensinfinRuntimeExceptionConverter().convert( appException );
		}
	}

	public ResponseEntity<RequestV2> newRequest( final RequestV2 newRequest ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Request.";
		final Response<RequestV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV2.class )
				.newRequest( newRequest )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
