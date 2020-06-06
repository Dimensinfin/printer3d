package org.dimensinfin.printer3d.backend.support.production.job.rest;

import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.core.AcceptanceTargetConfig;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiV1;

import retrofit2.Response;
import retrofit2.Retrofit;

public class JobFeignClientV1 extends CommonFeignClient {

	// - C O N S T R U C T O R S
	public JobFeignClientV1( final @NotNull AcceptanceTargetConfig acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<List<Job>> getPendingJobs() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list pending jobs.";
		final Response<List<Job>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiV1.class )
				.getPendingJobs()
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

}
