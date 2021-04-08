package org.dimensinfin.printer3d.backend.support.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiSupport;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.15.0
 */
public class Printer3DFeignClientSupport extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public Printer3DFeignClientSupport( final @NotNull ITargetConfiguration acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<CounterResponse> resetCoilScheduler() throws IOException {
		final String ENDPOINT_MESSAGE = "Clear Coil destruction time to the current time.";
		final Response<CounterResponse> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiSupport.class )
				.resetCoilScheduler()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}