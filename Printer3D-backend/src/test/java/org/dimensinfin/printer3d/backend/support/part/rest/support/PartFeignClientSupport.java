package org.dimensinfin.printer3d.backend.support.part.rest.support;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.part.support.PartsApiSupport;

import retrofit2.Response;
import retrofit2.Retrofit;

public class PartFeignClientSupport extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public PartFeignClientSupport( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public Integer deleteAllParts() {
		final String ENDPOINT_MESSAGE = "Request to delete all Part records.";
		try {
			final Response<CountResponse> response = new Retrofit.Builder()
					.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
					.addConverterFactory( GSON_CONVERTER_FACTORY )
					.build()
					.create( PartsApiSupport.class )
					.deleteAllParts()
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
