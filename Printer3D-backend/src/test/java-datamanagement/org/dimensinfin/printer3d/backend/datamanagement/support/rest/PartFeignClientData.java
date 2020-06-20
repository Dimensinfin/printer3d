package org.dimensinfin.printer3d.backend.datamanagement.support.rest;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.datamanagement.support.domain.PartData;
import org.dimensinfin.printer3d.backend.datamanagement.support.domain.PartListData;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;

import retrofit2.Response;
import retrofit2.Retrofit;

public class PartFeignClientData extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public PartFeignClientData( final @NotNull ITargetConfiguration acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public ResponseEntity<PartListData> getParts( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of all parts including not active.";
		final Response<PartListData> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiData.class )
				.getParts( authorizationToken, false )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<PartData> updatePart( final String authorizationToken, final PartData part ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the update of an existing Part.";
		final Response<PartData> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( InventoryApiData.class )
				.updatePart( authorizationToken, part )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
