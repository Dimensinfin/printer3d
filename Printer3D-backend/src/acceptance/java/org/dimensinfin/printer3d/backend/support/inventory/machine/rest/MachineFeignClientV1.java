package org.dimensinfin.printer3d.backend.support.inventory.machine.rest;

import java.io.IOException;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

import retrofit2.Response;
import retrofit2.Retrofit;

public class MachineFeignClientV1 extends CommonFeignClient {

	// - C O N S T R U C T O R S
	public MachineFeignClientV1( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<MachineV2> cancelBuild( final String authorizationToken, final UUID machineId ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to cancel a build job.";
		final Response<MachineV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.cancelBuild( authorizationToken, machineId )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<MachineV2> completeBuild( final UUID machineId ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to complete a build job.";
		final Response<MachineV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.completeBuild( machineId )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
