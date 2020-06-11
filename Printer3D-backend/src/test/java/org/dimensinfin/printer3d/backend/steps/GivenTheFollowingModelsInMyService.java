package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.model.CucumberTableToNewModelRequestConverter;
import org.dimensinfin.printer3d.backend.support.inventory.model.rest.ModelFeignClientV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;

import io.cucumber.java.en.Given;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PART_ID_LIST;

public class GivenTheFollowingModelsInMyService extends StepSupport {
	private final ModelFeignClientV1 modelFeignClientV1;

	// - C O N S T R U C T O R S
	public GivenTheFollowingModelsInMyService( final @NotNull Printer3DWorld printer3DWorld,
	                                           final @NotNull ModelFeignClientV1 modelFeignClientV1 ) {
		super( printer3DWorld );
		this.modelFeignClientV1 = Objects.requireNonNull( modelFeignClientV1 );
	}

	@Given("the following Models in my service")
	public void the_following_Models_in_my_service( final List<Map<String, String>> dataTable ) throws IOException {
		for (int i = 0; i < dataTable.size(); i++) {
			final Map<String, String> row = dataTable.get( i );
			final NewModelRequest newModelRequest = new CucumberTableToNewModelRequestConverter().convert( row );
			// Create the model.
			this.modelFeignClientV1.newModel( this.printer3DWorld.getJwtAuthorizationToken(), newModelRequest );
			// Add the list of parts
			this.addPartReferences( row.get( ID ), row.get( PART_ID_LIST ) );
		}
	}

	protected void addPartReferences( final String modelId, final String partRawList ) throws IOException {
		final String[] partList = partRawList.split( "," );
		for (int i = 0; i < partList.length; i++) {
			this.modelFeignClientV1.addModelPart( this.printer3DWorld.getJwtAuthorizationToken(),
					UUID.fromString( modelId.trim() ),
					UUID.fromString( partList[i].trim() )
			);
		}
	}
}
