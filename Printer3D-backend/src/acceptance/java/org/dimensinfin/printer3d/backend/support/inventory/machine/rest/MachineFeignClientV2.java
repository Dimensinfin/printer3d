package org.dimensinfin.printer3d.backend.support.inventory.machine.rest;

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
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

import retrofit2.Response;
import retrofit2.Retrofit;

public class MachineFeignClientV2 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public MachineFeignClientV2( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<List<MachineV2>> getMachines( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of all the Machines.";
		final Response<List<MachineV2>> response = new Retrofit.Builder()
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

	public ResponseEntity<MachineV2> startBuild( final String authorizationToken,
	                                             final UUID machineId,
	                                             final JobRequest jobRequest ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request to start a new build job.";
		final Response<MachineV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiV2.class )
				.startBuild( authorizationToken, machineId, jobRequest )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else {
			LogWrapper.info( ENDPOINT_MESSAGE + " Failed." );
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
		}
	}
}
