package org.dimensinfin.printer3d.backend.support.inventory.machine.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListv2;

import retrofit2.Response;
import retrofit2.Retrofit;

public class MachineFeignClientV2 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public MachineFeignClientV2( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<MachineListv2> getMachines( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of all tth Machines.";
		final Response<MachineListv2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV2.class )
				.getMachines()
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

}
