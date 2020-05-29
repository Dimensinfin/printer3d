package org.dimensinfin.printer3d.backend.support.roll.rest.v1;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.domain.RollList;
import org.dimensinfin.printer3d.client.part.rest.InventoryApiV1;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RollFeignClientV1 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RollFeignClientV1( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<Roll> newRoll( final String authorizationToken, final Roll roll ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Roll.";
		final Response<Roll> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.newRoll( authorizationToken, roll )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
	public ResponseEntity<RollList> getRolls ( final String authorizationToken) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Roll.";
		final Response<RollList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.getRolls( authorizationToken )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
