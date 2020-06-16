package org.dimensinfin.printer3d.backend.support.production.request.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiSupport;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestFeignClientSupport extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RequestFeignClientSupport( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<RequestList> getRepositoryRequests() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of Requests stored at the repository.";
		final Response<RequestList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiSupport.class )
				.getRepositoryRequests()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public Integer deleteAllRequests() {
		final String ENDPOINT_MESSAGE = "Request to delete all Request records.";
		try {
			final Response<CountResponse> response = new Retrofit.Builder()
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
}
