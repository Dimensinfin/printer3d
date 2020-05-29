package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.roll.CucumberTableToRollConverter;
import org.dimensinfin.printer3d.backend.support.roll.rest.v1.RollFeignClientV1;
import org.dimensinfin.printer3d.client.domain.RollList;

import io.cucumber.java.en.Given;

public class GivenTheFollowingRollsInMyService extends StepSupport {
	private final RollFeignClientV1 rollFeignClientV1;

	// - C O N S T R U C T O R S
	public GivenTheFollowingRollsInMyService( final @NotNull Printer3DWorld printer3DWorld,
	                                          final @NotNull RollFeignClientV1 rollFeignClientV1 ) {
		super( printer3DWorld );
		this.rollFeignClientV1 = Objects.requireNonNull( rollFeignClientV1 );
	}

	@Given("the following Rolls in my service")
	public void the_following_Rolls_in_my_service( final List<Map<String, String>> dataTable ) throws IOException {
		for (int i = 0; i < dataTable.size(); i++) {
			final Map<String, String> row = dataTable.get( i );
			final Roll roll = new CucumberTableToRollConverter( this.printer3DWorld ).convert( row );
			this.rollFeignClientV1.newRoll( this.printer3DWorld.getJwtAuthorizationToken(), roll );
		}
		final ResponseEntity<RollList> rollList = this.rollFeignClientV1.getRolls( this.printer3DWorld.getJwtAuthorizationToken() );
		Assertions.assertEquals( dataTable.size(), rollList.getBody().getCount() );
	}
}
