package org.dimensinfin.printer3d.backend.support.production.request.rest;

import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestFeignClientV2 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public RequestFeignClientV2( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<List<RequestV2>> getOpenRequests() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of Open v2 requests.";
		final Response<List<RequestV2>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV2.class )
				.getOpenRequests()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<RequestV2> newRequest( final RequestV2 newRequest ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Request.";
		final Response<RequestV2> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV2.class )
				.newRequest( newRequest )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
