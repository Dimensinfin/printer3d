package org.dimensinfin.printer3d.backend.support.inventory.part.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.core.AppErrorInfo;
import org.dimensinfin.printer3d.backend.support.core.AppErrorInfoConverter;
import org.dimensinfin.printer3d.backend.support.core.AppErrorInfoToDimensinfinRuntimeExceptionConverter;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessage;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessageConverter;
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;

import retrofit2.Response;
import retrofit2.Retrofit;

public class PartFeignClientV1 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public PartFeignClientV1( final @NotNull ITargetConfiguration acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public Integer countParts( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the count of the part list.";
		final Response<PartList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.getParts( authorizationToken, false )
				.execute();
		if (response.isSuccessful()) {
			return response.body().getCount();
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<PartList> getParts( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of all parts including not active.";
		final Response<PartList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.getParts( authorizationToken, false )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<Part> newPart( final String authorizationToken, final Part part ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Part.";
		final Response<Part> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.newPart( authorizationToken, part )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else {
			if (response.code() == 400) {
				final RestExceptionMessage restException = new RestExceptionMessageConverter().convert( response.errorBody().string() );
				final String message = restException.getErrors().get( 0 ).getDefaultMessage();
				throw new DimensinfinRuntimeException( ErrorInfo.INVALID_REQUEST_STRUCTURE, message );
			}
			final AppErrorInfo appException = new AppErrorInfoConverter().convert( response.errorBody().string() );
			throw new AppErrorInfoToDimensinfinRuntimeExceptionConverter().convert( appException );
		}
	}

	public ResponseEntity<Part> updatePart( final String authorizationToken, final Part part ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the update of an existing Part.";
		final Response<Part> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.updatePart( authorizationToken, part )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
