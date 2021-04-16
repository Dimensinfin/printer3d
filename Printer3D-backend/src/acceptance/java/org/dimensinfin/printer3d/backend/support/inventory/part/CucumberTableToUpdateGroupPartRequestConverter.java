package org.dimensinfin.printer3d.backend.support.inventory.part;

import java.util.Map;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PROJECT;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEIGHT;

public class CucumberTableToUpdateGroupPartRequestConverter extends CucumberTableConverter<UpdateGroupPartRequest> {
	@Override
	public UpdateGroupPartRequest convert( final Map<String, String> cucumberRow ) {
		UpdateGroupPartRequest.Builder builder = new UpdateGroupPartRequest.Builder();
		if (null != cucumberRow.get( PROJECT )) builder = builder.withProject( cucumberRow.get( PROJECT ) );
		if (null != cucumberRow.get( DESCRIPTION )) builder = builder.withDescription( cucumberRow.get( DESCRIPTION ) );
		if (null != cucumberRow.get( WEIGHT )) builder = builder.withWeight( Integer.parseInt( cucumberRow.get( WEIGHT ) ) );
		if (null != cucumberRow.get( BUILD_TIME )) builder = builder.withBuildTime( Integer.parseInt( cucumberRow.get( BUILD_TIME ) ) );
		if (null != cucumberRow.get( IMAGE_PATH )) builder = builder.withImagePath( cucumberRow.get( IMAGE_PATH ) );
		if (null != cucumberRow.get( MODEL_PATH )) builder = builder.withModelPath( cucumberRow.get( MODEL_PATH ) );
		return builder.build();
	}
}
