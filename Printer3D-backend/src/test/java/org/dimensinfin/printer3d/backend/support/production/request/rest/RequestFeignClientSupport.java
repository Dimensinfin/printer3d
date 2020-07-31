package org.dimensinfin.printer3d.backend.support.production.request.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiSupport;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestFeignClientSupport extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RequestFeignClientSupport( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public Integer deleteAllRequests() {
		final String ENDPOINT_MESSAGE = "Request to delete all Request records.";
		try {
			final Response<CounterResponse> response = new Retrofit.Builder()
					.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
					.addConverterFactory( GSON_CONVERTER_FACTORY )
					.build()
					.create( ProductionApiSupport.class )
					.deleteAllRequests()
					.execute();
			if (response.isSuccessful()) {
				LogWrapper.info( ENDPOINT_MESSAGE );
				return response.body().getRecords();
			} else throw new RuntimeException( "Error information" );
		} catch (final IOException ioe) {
			throw new RuntimeException( ioe.getMessage() );
		}
	}

	public Integer deleteAllRequestsV2() {
		final String ENDPOINT_MESSAGE = "Request to delete all Request v2 records.";
		try {
			final Response<CounterResponse> response = new Retrofit.Builder()
					.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
					.addConverterFactory( GSON_CONVERTER_FACTORY )
					.build()
					.create( ProductionApiSupport.class )
					.deleteAllRequestsV2()
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
