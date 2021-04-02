package org.dimensinfin.printer3d.backend.support.core;

import org.dimensinfin.acceptance.support.CommonWorld;

public class CommonValidator {
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
			if (null != recordValue) return cucumberData;
		if (cucumberData.contains( "null" ))
			if (null == recordValue) return cucumberData;
		return recordValue;
	}
}
