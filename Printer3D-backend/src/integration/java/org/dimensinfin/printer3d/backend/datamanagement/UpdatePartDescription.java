package org.dimensinfin.printer3d.backend.datamanagement;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.conf.IntegrationTargetConfig;
import org.dimensinfin.printer3d.backend.support.inventory.part.rest.PartFeignClientV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

public class UpdatePartDescription {
	private static final String NEW_DESCRIPTION = "Llavero ornamental.";
	private static final String TARGET_LABEL = "COVID KEY 1.0";
	private ITargetConfiguration targetConfiguration;
	private Printer3DWorld printer3DWorld;
	private PartFeignClientV2 partFeignClientV2;

	@BeforeEach
	public void beforeEach() {
		this.printer3DWorld = new Printer3DWorld();
		this.targetConfiguration = new IntegrationTargetConfig();
	}

	//	@Test
	public void updatePartDescriptionIT() throws IOException {
		// Setup the target configuration to the desired environment.
		this.partFeignClientV2 = new PartFeignClientV2( Objects.requireNonNull( this.targetConfiguration ) );
		this.printer3DWorld.setJwtAuthorizationToken( "-NOT-REQUIRED-TOKEN-" );

		// Read the list of Parts and replace the Description. Rewrite the new Part to the repository.
		this.partFeignClientV2.getPartsV2( this.printer3DWorld.getJwtAuthorizationToken() ).getBody()
				.stream()
				.filter( ( part ) -> {
					LogWrapper.info( "label: " + part.getLabel() );
					return part.getLabel().equalsIgnoreCase( "Covid-19 Key v1.0" );
				} )
				.forEach( ( part ) -> {
					final UpdateGroupPartRequest updateData = new UpdateGroupPartRequest.Builder()
							.withDescription( NEW_DESCRIPTION )
							.build();
					try {
						LogWrapper.info( "Updating Part: " + part.getLabel() );
						LogWrapper.info( "Updating Description: " + part.getDescription() );
						this.partFeignClientV2.updateGroupPart( updateData );
					} catch (final IOException ioe) {
						LogWrapper.error( ioe );
					}
				} );
	}
}
