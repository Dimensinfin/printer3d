package org.dimensinfin.printer3d.backend.support.part.rest.v1;

import java.io.IOException;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.part.domain.PartList;
import org.dimensinfin.printer3d.client.part.v1.PartsApiV1;

import retrofit2.Response;
import retrofit2.Retrofit;

public class PartFeignClientV1 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public PartFeignClientV1( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	public Integer countParts( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the count of the part list.";
		final Response<PartList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( PartsApiV1.class )
				.partList( authorizationToken, true )
				.execute();
		if (response.isSuccessful()) {
			return response.body().getCount();
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<Part> newPart( final String authorizationToken, final Part part ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the creation of a new Part.";
		final Response<Part> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( PartsApiV1.class )
				.newPart( authorizationToken, part )
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public ResponseEntity<PartList> partList( final String authorizationToken ) throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of all parts including not active.";
		final Response<PartList> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( PartsApiV1.class )
				.partList( authorizationToken, true )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}
}
