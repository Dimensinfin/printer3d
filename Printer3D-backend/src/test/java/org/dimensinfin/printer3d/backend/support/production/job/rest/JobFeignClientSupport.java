package org.dimensinfin.printer3d.backend.support.production.job.rest;

import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.client.production.rest.ProductionApiSupport;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

import retrofit2.Response;
import retrofit2.Retrofit;

public class JobFeignClientSupport extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public JobFeignClientSupport( final @NotNull ITargetConfiguration acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<List<JobHistoric>> getCompletedJobs() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the list of completed historic Jobs.";
		final Response<List<JobHistoric>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( ProductionApiSupport.class )
				.getCompletedJobs()
				.execute();
		if (response.isSuccessful()) {
			LogWrapper.info( ENDPOINT_MESSAGE );
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else throw new IOException( ENDPOINT_MESSAGE + " Failed." );
	}

	public Integer clearJobsRepositoryTable() {
		final String ENDPOINT_MESSAGE = "Request to delete all Job records.";
		try {
			final Response<CountResponse> response = new Retrofit.Builder()
					.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
					.addConverterFactory( GSON_CONVERTER_FACTORY )
					.build()
					.create( ProductionApiSupport.class )
					.deleteAllJobs()
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
