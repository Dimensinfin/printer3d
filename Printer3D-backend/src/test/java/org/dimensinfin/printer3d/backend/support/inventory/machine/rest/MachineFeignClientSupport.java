package org.dimensinfin.printer3d.backend.support.inventory.machine.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiSupport;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

import retrofit2.Response;
import retrofit2.Retrofit;

public class MachineFeignClientSupport extends CommonFeignClient {

	// - C O N S T R U C T O R S
	public MachineFeignClientSupport( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<Boolean> setupMachine( final SetupRequest setupRequest ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to configure the fields for a Machine.";
		final Response<Boolean> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiSupport.class )
				.setupMachine( setupRequest )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

}
