package org.dimensinfin.printer3d.backend.support.accounting.rest;

import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.core.CommonFeignClient;
import org.dimensinfin.printer3d.backend.support.core.RestExceptionMessageConverter;
import org.dimensinfin.printer3d.client.accounting.rest.AccountingApiV1;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountFeignClientV1 extends CommonFeignClient {
	// - C O N S T R U C T O R S
	public AccountFeignClientV1( final @NotNull ITargetConfiguration acceptanceTargetConfig ) {
		super( acceptanceTargetConfig );
	}

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<List<WeekAmount>> getRequestsAmountPerWeek() throws IOException {
		final String ENDPOINT_MESSAGE = "Request the amounts aggregated per week rom Requests.";
		final Response<List<WeekAmount>> response = new Retrofit.Builder()
				.baseUrl( this.acceptanceTargetConfig.getBackendServer() )
				.addConverterFactory( GSON_CONVERTER_FACTORY )
				.build()
				.create( AccountingApiV1.class )
				.getRequestsAmountPerWeek( 6 )
				.execute();
		if (response.isSuccessful()) {
			return new ResponseEntity<>( response.body(), HttpStatus.valueOf( response.code() ) );
		} else
			throw new DimensinfinRuntimeException( new RestExceptionMessageConverter().convert( response.errorBody().string() ) );
	}
}
