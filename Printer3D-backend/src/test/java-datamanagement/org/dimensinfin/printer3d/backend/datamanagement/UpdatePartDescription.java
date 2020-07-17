package org.dimensinfin.printer3d.backend.datamanagement;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.datamanagement.support.rest.PartFeignClientData;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.conf.ITargetConfiguration;
import org.dimensinfin.printer3d.backend.support.conf.IntegrationTargetConfig;
import org.dimensinfin.printer3d.backend.support.conf.ProductionTargetConfig;

public class UpdatePartDescription {
	private static final String NEW_DESCRIPTION = "Llavero ornamental.";
	private static final String TARGET_LABEL="COVID KEY 1.0";
	private ITargetConfiguration targetConfiguration;
	private Printer3DWorld printer3DWorld;
	private PartFeignClientData partFeignClientData;

	@BeforeEach
	public void beforeEach() {
		this.printer3DWorld = new Printer3DWorld();
	}

	//	@Test
	public void updatePartDescriptionIntegration() throws IOException {
		// Setup the target configuration to the desired environment.
		this.targetConfiguration = new IntegrationTargetConfig();
		this.partFeignClientData = new PartFeignClientData( Objects.requireNonNull( this.targetConfiguration ) );
		this.printer3DWorld.setJwtAuthorizationToken( "-NOT-REQUIRED-TOKEN-" );

		// Read the list of Parts and replace the Description. Rewrite the new Part to the repository.
		this.partFeignClientData.getParts( this.printer3DWorld.getJwtAuthorizationToken() ).getBody().getParts()
				.stream()
				.filter( ( part ) -> {
					LogWrapper.info( "label: " + part.getLabel() );
					return part.getLabel().equalsIgnoreCase( "Covid-19 Key v1.0" );
				} )
				.forEach( ( part ) -> {
					part.setDescription( NEW_DESCRIPTION );
					try {
						LogWrapper.info( "Updating Part: " + part.getLabel() );
						LogWrapper.info( "Updating Description: " + part.getDescription() );
						this.partFeignClientData.updatePart( this.printer3DWorld.getJwtAuthorizationToken(), part );
					} catch (final IOException ioe) {
						LogWrapper.error( ioe );
					}
				} );
	}

	//	@Test
	public void updatePartDescriptionProduction() throws IOException {
		// Setup the target configuration to the desired environment.
		this.targetConfiguration = new ProductionTargetConfig();
		this.partFeignClientData = new PartFeignClientData( Objects.requireNonNull( this.targetConfiguration ) );
		this.printer3DWorld.setJwtAuthorizationToken( "-NOT-REQUIRED-TOKEN-" );

		// Read the list of Parts and replace the Description. Rewrite the new Part to the repository.
		this.partFeignClientData.getParts( this.printer3DWorld.getJwtAuthorizationToken() ).getBody().getParts()
				.stream()
				.filter( ( part ) -> {
					LogWrapper.info( "label: " + part.getLabel() );
					return part.getLabel().equalsIgnoreCase( TARGET_LABEL);
				} )
				.forEach( ( part ) -> {
					part.setDescription( NEW_DESCRIPTION );
					try {
						LogWrapper.info( "Updating Part: " + part.getLabel() );
						LogWrapper.info( "Updating Description: " + part.getDescription() );
						this.partFeignClientData.updatePart( this.printer3DWorld.getJwtAuthorizationToken(), part );
					} catch (final IOException ioe) {
						LogWrapper.error( ioe );
					}
				} );
	}
}
