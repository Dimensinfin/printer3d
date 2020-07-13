package org.dimensinfin.printer3d.backend.support.inventory.model.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.inventory.rest.InventoryApiSupport;

import retrofit2.Response;
import retrofit2.Retrofit;

public class ModelFeignClientSupport extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public ModelFeignClientSupport( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public Integer deleteAllModels() {
		final String ENDPOINT_MESSAGE = "Request to delete all Model records.";
		try {
			final Response<CounterResponse> response = new Retrofit.Builder()
					.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
					.addConverterFactory( GSON_CONVERTER_FACTORY )
					.build()
					.create( InventoryApiSupport.class )
					.deleteAllModels()
					.execute();
			if (response.isSuccessful()) {
				LogWrapper.info( ENDPOINT_MESSAGE );
				return response.body().getRecords();
			} else throw new RuntimeException( "Error information" );
		} catch (final IOException ioe) {
			throw new RuntimeException( ioe.getMessage() );
		}
	}
}
