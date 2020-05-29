package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.part.converter.CucumberTableToPartConverter;
import org.dimensinfin.printer3d.backend.support.part.rest.v1.PartFeignClientV1;

import io.cucumber.java.en.Given;

public class GivenTheFollowingPartsInMyService extends StepSupport {
	private PartFeignClientV1 partFeignClientV1;

	// - C O N S T R U C T O R S
	public GivenTheFollowingPartsInMyService( final @NotNull Printer3DWorld printer3DWorld,
	                                          final @NotNull PartFeignClientV1 partFeignClientV1 ) {
		super( printer3DWorld );
		this.partFeignClientV1 = Objects.requireNonNull( partFeignClientV1 );
	}

	@Given("the following Parts in my service")
	public void the_following_Parts_in_my_service( final List<Map<String, String>> dataTable ) throws IOException {
		for (int i = 0; i < dataTable.size(); i++) {
			final Map<String, String> row = dataTable.get( i );
			final Part part = new CucumberTableToPartConverter().convert( row );
			this.partFeignClientV1.newPart( this.printer3DWorld.getJwtAuthorizationToken(), part );
		}
		Assertions.assertEquals( dataTable.size(), this.partFeignClientV1.countParts( this.printer3DWorld.getJwtAuthorizationToken() ).intValue() );
	}
}
