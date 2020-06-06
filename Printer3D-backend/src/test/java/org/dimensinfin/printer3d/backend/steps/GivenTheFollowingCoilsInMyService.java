package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.roll.CucumberTableToCoilConverter;
import org.dimensinfin.printer3d.backend.support.roll.rest.v1.CoilFeignClientV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;

import io.cucumber.java.en.Given;

public class GivenTheFollowingCoilsInMyService extends StepSupport {
	private final CoilFeignClientV1 coilFeignClientV1;

	// - C O N S T R U C T O R S
	public GivenTheFollowingCoilsInMyService( final @NotNull Printer3DWorld printer3DWorld,
	                                          final @NotNull CoilFeignClientV1 coilFeignClientV1 ) {
		super( printer3DWorld );
		this.coilFeignClientV1 = Objects.requireNonNull( coilFeignClientV1 );
	}

	@Given("the following Coils in my service")
	public void the_following_Coils_in_my_service( final List<Map<String, String>> dataTable ) throws IOException {
		for (int i = 0; i < dataTable.size(); i++) {
			final Map<String, String> row = dataTable.get( i );
			final Coil coil = new CucumberTableToCoilConverter( this.printer3DWorld ).convert( row );
			this.coilFeignClientV1.newCoil( this.printer3DWorld.getJwtAuthorizationToken(), coil );
		}
		final ResponseEntity<CoilList> coilList = this.coilFeignClientV1.getCoils( this.printer3DWorld.getJwtAuthorizationToken() );
		Assertions.assertEquals( dataTable.size(), coilList.getBody().getCount() );
	}
}
