package org.dimensinfin.printer3d.backend.support.roll.rest.v1;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;
import org.dimensinfin.printer3d.client.domain.CoilList;
import org.dimensinfin.printer3d.client.part.rest.InventoryApiV1;

import retrofit2.Response;
import retrofit2.Retrofit;

public class CoilFeignClientV1 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public CoilFeignClientV1( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<FinishingsResponse> getFinishings( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of Finishings to report the available colors.";
		final Response<FinishingsResponse> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.getFinishings( authorizationToken )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<CoilList> getCoils( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Roll.";
		final Response<CoilList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.getCoils( authorizationToken )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<Coil> newCoil( final String authorizationToken, final Coil coil ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Roll.";
		final Response<Coil> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.newCoil( authorizationToken, coil )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
