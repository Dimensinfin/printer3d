package org.dimensinfin.printer3d.client.inventory.rest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_WEIGHT;

public class UpdateGroupPartRequestTest {
	@Test
	public void buildContract() {
		final UpdateGroupPartRequest updateGroupPartRequest = new UpdateGroupPartRequest.Builder()
				.withLabel( TEST_PART_LABEL )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withDescription( TEST_PART_DESCRIPTION )
				.withWeight( TEST_PART_WEIGHT )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.build();
		Assertions.assertNotNull( updateGroupPartRequest );
	}

	@Test
	public void getterContract() {
		// Given
		final UpdateGroupPartRequest updateGroupPartRequest = new UpdateGroupPartRequest.Builder()
				.withLabel( TEST_PART_LABEL )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withDescription( TEST_PART_DESCRIPTION )
				.withWeight( TEST_PART_WEIGHT )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_PART_LABEL, updateGroupPartRequest.getLabel() );
		updateGroupPartRequest.setLabel( "New Label" );
		Assertions.assertEquals( "New Label", updateGroupPartRequest.getLabel() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, updateGroupPartRequest.getBuildTime() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION, updateGroupPartRequest.getDescription() );
		Assertions.assertEquals( TEST_PART_WEIGHT, updateGroupPartRequest.getWeight() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH, updateGroupPartRequest.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH, updateGroupPartRequest.getModelPath() );
	}
}
