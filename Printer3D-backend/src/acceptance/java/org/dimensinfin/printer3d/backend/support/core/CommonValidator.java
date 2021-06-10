package org.dimensinfin.printer3d.backend.support.core;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.CommonWorld;

public class CommonValidator {
	public enum EFieldType {
		STRING, INTEGER, FLOAT
	}

	protected String cleanKey( final String key ) {
		return key.replace( '>', ' ' ).trim();
	}

	protected String cucumberDataMap( final CommonWorld world, final String cucumberData ) {
		if (cucumberData.contains( "world" )) {
			final String key = this.cleanKey( cucumberData.split( ":" )[1] );
			switch (key) {
				case "id":
					return world.getId().toString();
			}
		}
		return cucumberData;
	}

	protected String cucumberDataReplacer( final String cucumberData, final String recordValue ) {
		if (cucumberData.contains( "defined" ))
			if (null != recordValue) return recordValue;
		if (cucumberData.contains( "null" ))
			if (null == recordValue) return cucumberData;
		if (cucumberData.contains( "now" ))
			return recordValue;
		return recordValue;
	}

	protected String fieldValidation( final Map<String, String> rowData,
	                                  final String fieldName,
	                                  final Integer recordTarget,
	                                  final EFieldType type ) {
		switch (type) {
			case INTEGER:
				if (null != rowData.get( fieldName )) Assertions.assertEquals(
						Integer.parseInt( rowData.get( fieldName ) ), recordTarget
				);
				break;
			default:
				if (null != rowData.get( fieldName )) Assertions.assertEquals( rowData.get( fieldName ), recordTarget );
		}
		return recordTarget.toString();
	}
}
