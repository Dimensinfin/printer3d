package org.dimensinfin.printer3d.backend.support.inventory.machine.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.machine.persistence.Machine;
import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.domain.MachineList;
import org.dimensinfin.printer3d.client.domain.StartBuildRequest;
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiV1;

import retrofit2.Response;
import retrofit2.Retrofit;

public class MachineFeignClientV1 extends CommonFeignClient {

	// - C O N S T R U C T O R S
	public MachineFeignClientV1( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<MachineList> getMachines( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of all tth Machines.";
		final Response<MachineList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.getMachines( authorizationToken )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<Machine> startBuild( final String authorizationToken, final StartBuildRequest startBuildRequest ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to start a new build job.";
		final Response<Machine> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV1.class )
				.startBuild( authorizationToken, startBuildRequest.getMachineId(), startBuildRequest.getPartId() )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
